package com.ktk.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ktk.exception.RedditException;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtProvider {

    private String secretKey;

	private final long accessExpireTime = 60 * 60 * 1000L; 		// 1시간
	private final long refreshExpireTime = 60 * 60 * 48000L; 	// 2일

    @PostConstruct
    public void init() {
    	secretKey = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        
        
        SecretKey Key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        
        
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setExpiration(new Date(accessExpireTime))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
    }
}