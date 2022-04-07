package com.example.springbootproject.excptions;

public class PostNotFoundException extends IllegalArgumentException{

    public PostNotFoundException(String s) {
        super(s);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
