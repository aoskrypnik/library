package com.ukma.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ukma.library.model.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserWithConfPassDto {
    private static final String ISO_DATE_FORMATTER = "yyyy-MM-dd";

    private String username;
    private String password;
    private String confirmationPassword;
    private String realName;
    private String surname;
    private String phoneNumber;
    @JsonFormat(pattern = ISO_DATE_FORMATTER)
    private LocalDate birthDate;
    private String email;
    private UserRole userRole;
}
