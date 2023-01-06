package com.ktk.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktk.domain.entity.RefreshToken;
import com.ktk.exception.RedditException;
import com.ktk.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = RefreshToken.builder()
												.refreshToken(UUID.randomUUID().toString().replaceAll("-", ""))
												.createdDate(Instant.now())
												.build();
		refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}
	
	public void validateRefreshToken(String token) {
		refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new RedditException("invalidate refresh token"));
	}
	
	@Transactional
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByRefreshToken(token);
	}
	
}
