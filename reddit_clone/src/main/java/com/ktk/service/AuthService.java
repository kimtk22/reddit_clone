package com.ktk.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktk.domain.dto.AuthenticationResponse;
import com.ktk.domain.dto.LoginRequest;
import com.ktk.domain.dto.RefreshTokenRequest;
import com.ktk.domain.dto.RegisterRequest;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.NotificationEmail;
import com.ktk.domain.entity.VerificationToken;
import com.ktk.exception.RedditException;
import com.ktk.repository.MemberRepository;
import com.ktk.repository.VerificationTokenRepository;
import com.ktk.util.Constans;
import com.ktk.util.JwtProvider;
import com.ktk.util.MailContentBuilder;
import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final MailService mailService;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final MailContentBuilder mailContentBuilder;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	private final VerificationTokenRepository verificationTokenRepository;
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
        
        String accessToken = jwtProvider.generateToken(authenticate);
        String refreshToken = refreshTokenService.generateRefreshToken().getRefreshToken();
        
        return AuthenticationResponse.builder()
        						.email(loginRequest.getEmail())
        						.refreshToken(refreshToken)
        						.accessToken(accessToken)
        						.expiresAt(jwtProvider.getExpireFromJWT(accessToken))
        						.build();
    }
	
	@Transactional
	public void singup(RegisterRequest registerRequest) {
		
		if(isExistEmail(registerRequest.getEmail())) {
			throw new RedditException("Duplicate Email - " + registerRequest.getEmail());
		}
		
		Member member = Member.builder()
							.name(registerRequest.getUsername())
							.password(passwordEncoder.encode(registerRequest.getPassword()))
							.email(registerRequest.getEmail())
							.created(new Timestamp(System.currentTimeMillis()))
							.enabled(true)
							.build();
		
		memberRepository.save(member);
		
		// email server connection time out.
//		String token = generateVerificationToken(member);
//		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : " + Constans.ACTIVATION_EMAIL + "/" + token);
//		mailService.sendMail(new NotificationEmail("Please Activate your account", member.getEmail(), message));
	}
	
	private boolean isExistEmail(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}
	
	private String generateVerificationToken(Member member) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = VerificationToken.builder()
													.member(member)
													.token(token)
													.build();
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new RedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getMember().getName();
        Member member = memberRepository.findByName(username).orElseThrow(() -> new RedditException("User Not Found with id - " + username));
        member.setEnabled(true);
        memberRepository.save(member);
    }
    
    public Member getCurrentMember() {
    	User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return memberRepository.findByEmail(principal.getUsername())
    							.orElseThrow(() -> new UsernameNotFoundException("user name not found - " + principal.getUsername()));
    	
    }
    
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
    	refreshTokenService.validateRefreshToken(request.getRefreshToken());
    	
    	String accessToken = jwtProvider.generateTokenWithUserName(request.getEmail());
    	return AuthenticationResponse.builder()
				.email(request.getEmail())
				.refreshToken(request.getRefreshToken())
				.accessToken(accessToken)
				.expiresAt(jwtProvider.getExpireFromJWT(accessToken))
				.build();
    }
}
