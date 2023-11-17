package com.app.exceptions;

public class FailedToRegisterDriverException extends RuntimeException{
    public FailedToRegisterDriverException(String message) {
        super(message);
    }
}
