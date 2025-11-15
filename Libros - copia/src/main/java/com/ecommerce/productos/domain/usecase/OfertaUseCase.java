package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.IntercambioNoEncontradoException;
import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.domain.model.gateway.OfertaGateway;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class OfertaUseCase {

    private final OfertaGateway ofertaGateway;
    private final IntercambioUseCase intercambioUseCase;

    public OfertaIntercambio crearOferta(Long intercambioId, String mensaje) {
        // Verificar que el intercambio existe
        intercambioUseCase.consultarIntercambio(intercambioId);

        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setIntercambioId(intercambioId);
        oferta.setMensaje(mensaje);
        oferta.setAceptada(false);

        return ofertaGateway.guardar(oferta);
    }

    public List<OfertaIntercambio> buscarOfertasPorIntercambio(Long intercambioId) {
        return ofertaGateway.buscarPorIntercambioId(intercambioId);
    }

    public OfertaIntercambio aceptarOferta(Long ofertaId) {
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Oferta no encontrada"));

        oferta.setAceptada(true);
        return ofertaGateway.guardar(oferta);
    }
}