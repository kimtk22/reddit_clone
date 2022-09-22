package com.ktk.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	@NotEmpty(message = "이름 없음.")
	private String username;
	
	@Email
	@NotEmpty(message = "이메일 없음.")
	private String email;
	
	@NotEmpty(message = "패스워드 없음.")
	private String password;
}
