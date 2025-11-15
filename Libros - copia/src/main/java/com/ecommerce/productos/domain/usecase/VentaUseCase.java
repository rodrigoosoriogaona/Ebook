package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.TransaccionNoEncontradaException;
import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.domain.model.gateway.VentaGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VentaUseCase {

    private final VentaGateway ventaGateway;
    private final TransaccionUseCase transaccionUseCase;

    public Venta crearVenta(Long transaccionId, String metodoPago, String direccionEnvio) {
        Transaccion transaccion = transaccionUseCase.consultarTransaccion(transaccionId);

        Venta venta = new Venta();
        venta.setTransaccionId(transaccionId);
        venta.setMetodoPago(metodoPago);
        venta.setDireccionEnvio(direccionEnvio);
        venta.setEstadoEnvio("PENDIENTE");

        return ventaGateway.guardar(venta);
    }

    public Venta actualizarEstadoEnvio(Long ventaId, String estadoEnvio) {
        Venta venta = ventaGateway.buscarPorId(ventaId)
                .orElseThrow(() -> new TransaccionNoEncontradaException("Venta no encontrada"));

        venta.setEstadoEnvio(estadoEnvio);
        return ventaGateway.guardar(venta);
    }
}