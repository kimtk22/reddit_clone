package com.ktk.domain.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
	private String refreshToken;
	private String email;
}
