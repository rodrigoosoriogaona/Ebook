package com.ecommerce.libros.domain.exception;

public class TransaccionNoValidaException extends RuntimeException {
    public TransaccionNoValidaException(String message) {
        super(message);
    }
}
