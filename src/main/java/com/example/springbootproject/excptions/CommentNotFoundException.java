package com.example.springbootproject.excptions;


public class CommentNotFoundException extends IllegalArgumentException{

    public CommentNotFoundException(String s) {
        super(s);
    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
