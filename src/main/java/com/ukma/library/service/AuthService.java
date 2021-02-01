package com.ukma.library.service;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserCredentialsDto;

public interface AuthService {

	JwtResponseDto login(UserCredentialsDto credentials);
}
