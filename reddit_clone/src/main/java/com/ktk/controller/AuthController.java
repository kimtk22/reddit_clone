package com.ktk.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.domain.dto.AuthenticationResponse;
import com.ktk.domain.dto.LoginRequest;
import com.ktk.domain.dto.RegisterRequest;
import com.ktk.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok().build();
    }
	
	@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
		AuthenticationResponse authenticationResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authenticationResponse);
    }
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@Valid @RequestBody RegisterRequest request){
		authService.singup(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
