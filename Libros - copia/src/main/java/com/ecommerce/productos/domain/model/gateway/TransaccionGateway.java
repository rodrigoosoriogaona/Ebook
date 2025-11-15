package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Transaccion;
import java.util.List;
import java.util.Optional;

public interface TransaccionGateway {
    Transaccion guardar(Transaccion transaccion);
    Optional<Transaccion> buscarPorId(Long idTransaccion);
    List<Transaccion> buscarPorCompradorId(Long compradorId);
    List<Transaccion> buscarPorVendedorId(Long vendedorId);
    boolean existeTransaccion(Long idTransaccion);
}