package com.ktk.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ktk.exception.RedditException;

import javax.annotation.PostConstruct;
import java.security.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    private String secretKey = "asdfqwerasdfzxcvasdfqwerasdfzxcvasdf";
    
    @Value("${jwt.access.expire.time}")
	private long accessExpireTime;
    @Value("${jwt.refresh.expire.time}")
	private long refreshExpireTime;

    @PostConstruct
    public void init() {
//    	try {
//    		keyStore = KeyStore.getInstance("JKS");
//    		InputStream resourceStream = getClass().getResourceAsStream("/springblog.jks");
//    		keyStore.load(resourceStream, "secret".toCharArray());
//    	}catch (Exception e){
//    		throw new RedditException("Exception occurred while loading keystore");
//    	}
    	
    	secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setExpiration(Date.from(Instant.now().plusMillis(accessExpireTime)))
                //.signWith(getPrivateKey())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    
    public String generateTokenWithUserName(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(Date.from(Instant.now().plusMillis(accessExpireTime)))
                //.signWith(getPrivateKey())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    
    private PrivateKey getPrivateKey() {
    	try {
    		return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
    	} catch (Exception e) {
    		throw new RedditException("Exception occurred while get private key.");
    	}
    }
    
    public boolean validateToken(String jwt) {
    	//Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
    	
    	try {
    		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
    	} catch (Exception e) {
    		return false;
    	}
    	
    	return true;
    }
    
    private PublicKey getPublicKey() {
    	try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedditException("Exception occured while retrieving public key from keystore");
        }
    }
    
    public String getUsernameFromJWT(String token) {
    	Claims claims = Jwts.parser()
    						.setSigningKey(secretKey)
    						.parseClaimsJws(token)
    						.getBody();
    	return claims.getSubject();
    }
    
    public Instant getExpireFromJWT(String token) {
    	return Jwts.parser()
    				.setSigningKey(secretKey)
    				.parseClaimsJws(token)
    				.getBody()
    				.getExpiration().toInstant();
    }
    
}