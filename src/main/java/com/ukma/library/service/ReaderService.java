package com.ukma.library.service;

import com.ukma.library.dto.UserWithConfPassDto;
import com.ukma.library.model.User;

public interface ReaderService {
    User register(UserWithConfPassDto user);
    User getUser(String username);
}
