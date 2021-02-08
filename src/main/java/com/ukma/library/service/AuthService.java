package com.ukma.library.service;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.model.User;

public interface AuthService {

	JwtResponseDto login(UserCredentialsDto credentials);
}
