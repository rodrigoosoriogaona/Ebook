package com.ecommerce.libros.domain.model.Gateway;

public interface PagoGateway {
    boolean procesarPago(Long usuarioId, Double monto, String metodoPago);
    boolean reversarPago(Long usuarioId, Double monto); // CORREGIDO: debe ser reversarPago, no reversarPago
}