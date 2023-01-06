package com.ktk.domain.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
	private String email;
	private String refreshToken;
    private String accessToken;
    private Instant expiresAt;
}