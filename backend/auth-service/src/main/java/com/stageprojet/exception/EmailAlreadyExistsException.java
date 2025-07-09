package com.stageprojet.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Email is already registered");
    }
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}