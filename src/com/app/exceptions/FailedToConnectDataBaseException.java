package com.app.exceptions;

public class FailedToConnectDataBaseException extends RuntimeException{
    public FailedToConnectDataBaseException(Throwable cause) {
        super(cause.getMessage());
    }
}
