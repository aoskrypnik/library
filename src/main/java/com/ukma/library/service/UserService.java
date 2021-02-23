package com.ukma.library.service;

import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

	UserDetails areValidCredentials(UserCredentialsDto credentials);

	User saveUser(User user);

	User getCurrentUser();

	Optional<User> findByUsername(String username);
}
