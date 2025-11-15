package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.TransaccionNoEncontradaException;
import com.ecommerce.productos.domain.model.Compra;
import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.model.gateway.CompraGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompraUseCase {

    private final CompraGateway compraGateway;
    private final TransaccionUseCase transaccionUseCase;

    public Compra crearCompra(Long transaccionId, String direccionEntrega) {
        Transaccion transaccion = transaccionUseCase.consultarTransaccion(transaccionId);

        Compra compra = new Compra();
        compra.setTransaccionId(transaccionId);
        compra.setDireccionEntrega(direccionEntrega);
        compra.setEstadoCompra("PENDIENTE");

        return compraGateway.guardar(compra);
    }

    public Compra confirmarRecepcion(Long compraId) {
        Compra compra = compraGateway.buscarPorId(compraId)
                .orElseThrow(() -> new TransaccionNoEncontradaException("Compra no encontrada"));

        compra.setEstadoCompra("COMPLETADA");
        return compraGateway.guardar(compra);
    }
}