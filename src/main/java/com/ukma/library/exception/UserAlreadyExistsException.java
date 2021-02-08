package com.ukma.library.exception;

public class UserAlreadyExistsException extends RuntimeException{

    private static final String USER_ALREADY_EXISTS = "Such user already exists";

    public UserAlreadyExistsException(){
        super(USER_ALREADY_EXISTS);
    }
}
