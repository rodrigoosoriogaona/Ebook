package com.ecommerce.libros.domain.exception;

public class PublicacionNoActivaException extends RuntimeException {
    public PublicacionNoActivaException(String message) {
        super(message);
    }
}
