package com.ukma.library.exception;

public class AuthenticationException extends RuntimeException {

	private static final String CREDENTIALS_NOT_VALID = "Credentials not valid";

	public AuthenticationException() {
		super(CREDENTIALS_NOT_VALID);
	}
}
