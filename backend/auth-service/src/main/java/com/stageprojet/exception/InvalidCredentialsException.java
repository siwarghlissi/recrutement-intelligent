package com.stageprojet.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}