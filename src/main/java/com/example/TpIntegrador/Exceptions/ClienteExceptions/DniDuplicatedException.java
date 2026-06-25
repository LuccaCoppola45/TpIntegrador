package com.example.TpIntegrador.Exceptions.ClienteExceptions;

public class DniDuplicatedException extends RuntimeException {
    public DniDuplicatedException(String message) {
        super(message);
    }
}
