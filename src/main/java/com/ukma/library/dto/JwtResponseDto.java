package com.ukma.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class JwtResponseDto {
	private String jwt;
	private Date expirationDate;
	private String username;
	private String role;
}
