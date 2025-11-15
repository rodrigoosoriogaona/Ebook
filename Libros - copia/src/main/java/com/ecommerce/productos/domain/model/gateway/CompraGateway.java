package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Compra;
import java.util.Optional;

public interface CompraGateway {
    Compra guardar(Compra compra);
    Optional<Compra> buscarPorTransaccionId(Long transaccionId);
    Optional<Compra> buscarPorId(Long idCompra);
}