package com.ukma.library.service;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserWithConfPassDto;
import com.ukma.library.model.User;

public interface ReaderService {

	JwtResponseDto register(UserWithConfPassDto user);

	User getUserByUsername(String username);
}
