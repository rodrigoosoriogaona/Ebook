package com.ecommerce.libros.domain.exception;

public class TransaccionNoEncontradaException extends RuntimeException {
    public TransaccionNoEncontradaException(String message) {
        super(message);
    }
}
