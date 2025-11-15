package com.ecommerce.productos.domain.exception;

public class PagoFallidoException extends RuntimeException {
    public PagoFallidoException(String message) {
        super(message);
    }
}
