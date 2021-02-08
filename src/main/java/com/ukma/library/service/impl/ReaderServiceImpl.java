package com.ukma.library.service.impl;

import com.ukma.library.dto.UserWithConfPassDto;
import com.ukma.library.exception.PasswordNotMatchException;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.exception.UserAlreadyExistsException;
import com.ukma.library.model.User;
import com.ukma.library.model.UserRole;
import com.ukma.library.repository.UserRepository;
import com.ukma.library.service.ReaderService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class ReaderServiceImpl implements ReaderService {

    private static final String USER_NOT_FOUND = "User with such username not found";

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepository userRepository;

    @Override
    public User register(UserWithConfPassDto user) {

        if(user.getConfirmationPassword().isEmpty())
            throw new PasswordNotMatchException("Password confirmation can't be empty");
        if(!user.getConfirmationPassword().equals(user.getPassword()))
            throw new PasswordNotMatchException("Passwords should be equal");
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistsException();
        return userRepository.save(User.builder()
                                    .username(user.getUsername())
                                    .password(passwordEncoder.encode(user.getPassword()))
                                    .birthDate(user.getBirthDate())
                                    .realName(user.getRealName())
                                    .surname(user.getSurname())
                                    .userRole(UserRole.READER)
                                    .phoneNumber(user.getPhoneNumber())
                                    .email(user.getEmail())
                                    .registrationDate(LocalDate.now())
                                    .build());
    }

    @Override
    public User getUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

}
