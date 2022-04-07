package com.example.springbootproject.excptions;

public class CommunityNotFoundException extends IllegalArgumentException{

    public CommunityNotFoundException(String message) {
        super(message);
    }

    public CommunityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
