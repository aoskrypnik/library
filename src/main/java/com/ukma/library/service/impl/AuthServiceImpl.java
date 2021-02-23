package com.ukma.library.service.impl;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.exception.AuthenticationException;
import com.ukma.library.security.JwtTokenProvider;
import com.ukma.library.service.AuthService;
import com.ukma.library.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
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
		UserDetails userDetails = userService.areValidCredentials(credentials);
		if (userService.areValidCredentials(credentials) != null) {
			return jwtTokenProvider.generateToken(userDetails);
		} else {
			throw new AuthenticationException();
		}
	}
}
