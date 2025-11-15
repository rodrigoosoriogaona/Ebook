package com.ecommerce.pago.domain.usecase;

import com.ecommerce.pago.domain.exception.PagoFallidoException;
import com.ecommerce.pago.domain.exception.PagoNoEncontradoException;
import com.ecommerce.pago.domain.model.Pago;
import com.ecommerce.pago.domain.model.Gateway.PagoGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PagoUseCase {

    private final PagoGateway pagoGateway;

    public Pago procesarPago(Long transaccionId, Long usuarioId, Double monto, String metodoPago) {
        boolean pagoExitoso = simularProcesamientoPago();

        if (!pagoExitoso) {
            throw new PagoFallidoException("Error al procesar el pago");
        }

        Pago pago = new Pago();
        pago.setTransaccionId(transaccionId);
        pago.setUsuarioId(usuarioId);
        pago.setMonto(monto);
        pago.setMetodoPago(metodoPago);
        pago.setEstado("COMPLETADO");
        pago.setReferencia(generarReferencia());
        pago.setFechaCreacion(LocalDateTime.now());
        pago.setFechaActualizacion(LocalDateTime.now());

        return pagoGateway.guardar(pago);
    }

    public Pago revertirPago(Long pagoId) {
        Pago pago = pagoGateway.buscarPorId(pagoId)
                .orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado con ID: " + pagoId));

        pago.setEstado("REVERTIDO");
        pago.setFechaActualizacion(LocalDateTime.now());

        return pagoGateway.guardar(pago);
    }

    public Pago consultarPago(Long pagoId) {
        return pagoGateway.buscarPorId(pagoId)
                .orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado con ID: " + pagoId));
    }

    public Pago consultarPagoPorTransaccion(Long transaccionId) {
        return pagoGateway.buscarPorTransaccionId(transaccionId)
                .orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado para transacción: " + transaccionId));
    }

    private boolean simularProcesamientoPago() {
        return true;
    }

    private String generarReferencia() {
        return "REF-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
}