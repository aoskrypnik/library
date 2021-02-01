package com.ukma.library.controller;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Resource
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@RequestBody UserCredentialsDto credentials) {
		return ResponseEntity.ok(authService.login(credentials));
	}
}
