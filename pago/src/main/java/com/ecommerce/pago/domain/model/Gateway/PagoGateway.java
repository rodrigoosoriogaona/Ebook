package com.ecommerce.pago.domain.model.Gateway;

import com.ecommerce.pago.domain.model.Pago;
import java.util.Optional;

public interface PagoGateway {
    Pago guardar(Pago pago);
    Optional<Pago> buscarPorId(Long idPago);
    Optional<Pago> buscarPorTransaccionId(Long transaccionId);
    boolean existePago(Long idPago);
}