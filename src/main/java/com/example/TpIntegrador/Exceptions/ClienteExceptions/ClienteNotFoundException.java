package com.example.TpIntegrador.Exceptions.ClienteExceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
