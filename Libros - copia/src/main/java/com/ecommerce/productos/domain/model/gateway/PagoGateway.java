package com.ecommerce.productos.domain.model.gateway;

public interface PagoGateway {
    boolean procesarPago(Long transaccionId, Long usuarioId, Double monto);
    boolean revertirPago(String idTransaccionPago);
}
