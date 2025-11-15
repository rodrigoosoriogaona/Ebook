package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Venta;
import java.util.Optional;

public interface VentaGateway {
    Venta guardar(Venta venta);
    Optional<Venta> buscarPorTransaccionId(Long transaccionId);
    Optional<Venta> buscarPorId(Long idVenta);
}