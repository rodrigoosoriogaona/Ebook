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

    //Agregar tipoPago y publicacionId
    public Pago procesarPago(Long transaccionId, Long usuarioId, Double monto, String metodoPago, String tipoPago, Long publicacionId) {
        boolean pagoExitoso = simularProcesamientoPago();

        if (!pagoExitoso) {
            throw new PagoFallidoException("Error al procesar el pago");
        }

        Pago pago = new Pago();
        pago.setTransaccionId(transaccionId);
        pago.setUsuarioId(usuarioId);
        pago.setMonto(monto);
        pago.setMetodoPago(metodoPago);
        pago.setTipoPago(tipoPago);        // NUEVO
        pago.setPublicacionId(publicacionId); // NUEVO
        pago.setEstado("COMPLETADO");
        pago.setReferencia(generarReferencia());
        pago.setFechaCreacion(LocalDateTime.now());
        pago.setFechaActualizacion(LocalDateTime.now());

        return pagoGateway.guardar(pago);
    }

    // MÉTODO ACTUALIZADO: Agregar motivo de reversión
    public Pago revertirPago(Long pagoId, String motivo) {
        Pago pago = pagoGateway.buscarPorId(pagoId)
                .orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado con ID: " + pagoId));

        pago.setEstado("REVERTIDO");
        pago.setMotivoReversion(motivo); // NUEVO
        pago.setFechaActualizacion(LocalDateTime.now());

        return pagoGateway.guardar(pago);
    }

    // MÉTODOS EXISTENTES (sin cambios)
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