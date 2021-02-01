package com.ukma.library.service.impl;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.exception.AuthenticationException;
import com.ukma.library.security.JwtTokenProvider;
import com.ukma.library.service.AuthService;
import com.ukma.library.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

	@Resource
	private UserService userService;
	@Resource
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public JwtResponseDto login(UserCredentialsDto credentials) {
		if (userService.areValidCredentials(credentials)) {
			return jwtTokenProvider.generateToken(credentials.getUsername());
		} else {
			throw new AuthenticationException();
		}
	}
}
