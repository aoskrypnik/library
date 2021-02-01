package com.ukma.library.service;

import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	boolean areValidCredentials(UserCredentialsDto credentials);

	User saveUser(User user);
}