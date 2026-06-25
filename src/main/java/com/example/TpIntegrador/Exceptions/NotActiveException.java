package com.example.TpIntegrador.Exceptions;

public class NotActiveException extends RuntimeException {
    public NotActiveException(String message) {
        super(message);
    }
}
