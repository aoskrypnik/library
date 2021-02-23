package com.ukma.library.service.impl;

import com.ukma.library.dto.UserCredentialsDto;
import com.ukma.library.model.User;
import com.ukma.library.model.UserRole;
import com.ukma.library.repository.UserRepository;
import com.ukma.library.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

	private static final String READER_OR_ADMINISTRATOR_NOT_FOUND_WITH_USERNAME =
			"Reader or administrator not found with username ";

	@Resource
	private PasswordEncoder passwordEncoder;
	@Resource
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(READER_OR_ADMINISTRATOR_NOT_FOUND_WITH_USERNAME + username));
	}

	@Override
	public UserDetails areValidCredentials(UserCredentialsDto credentials) {
		try {
			UserDetails userDetails = loadUserByUsername(credentials.getUsername());
			return passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())
					? userDetails
					: null;
		} catch (UsernameNotFoundException exception) {
			return null;
		}
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@PostConstruct
	void buildTestUsers() {
		User administrator = User.builder()
				.username("admin")
				.password("adminPassword")
				.userRole(UserRole.ADMINISTRATOR)
				.build();

		User user = User.builder()
				.username("reader")
				.password("readerPassword")
				.realName("Andrew")
				.surname("Skrypnik")
				.phoneNumber("+380503333333")
				.birthDate(LocalDate.of(2000, 9, 12))
				.registrationDate(LocalDate.now())
				.email("andreus.sao@gmail.com")
				.userRole(UserRole.READER)
				.build();

		if (userRepository.findByUsername("reader").isEmpty()) {
			saveUser(user);
		}
		if (userRepository.findByUsername("admin").isEmpty()) {
			saveUser(administrator);
		}
	}
}
