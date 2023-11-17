package com.app.exceptions;

public class NoSuchColumnInResultSetException extends RuntimeException{
    public NoSuchColumnInResultSetException(Throwable cause) {
        super(cause);
    }
}
