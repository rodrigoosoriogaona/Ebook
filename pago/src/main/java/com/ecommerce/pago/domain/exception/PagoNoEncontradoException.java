package com.ecommerce.pago.domain.exception;

public class PagoNoEncontradoException extends RuntimeException {
    public PagoNoEncontradoException(String message) {
        super(message);
    }
}
