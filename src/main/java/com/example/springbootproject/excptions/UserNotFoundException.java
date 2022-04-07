package com.example.springbootproject.excptions;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
