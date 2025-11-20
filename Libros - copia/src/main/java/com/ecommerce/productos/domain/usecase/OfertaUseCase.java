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

    public OfertaIntercambio crearOferta(Long publicacionId, Long usuarioOferenteId, Long libroOfrecidoId, String mensaje) {
        // Verificar que la publicación existe - usando el método correcto
        intercambioUseCase.consultarPublicacion(publicacionId);

        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setPublicacionId(publicacionId); // Cambiado de intercambioId a publicacionId
        oferta.setUsuarioOferenteId(usuarioOferenteId);
        oferta.setLibroOfrecidoId(libroOfrecidoId);
        oferta.setMensaje(mensaje);
        oferta.setEstado("PENDIENTE"); // Cambiado de aceptada a estado
        oferta.setFechaCreacion(java.time.LocalDateTime.now());
        oferta.setFechaActualizacion(java.time.LocalDateTime.now());

        return ofertaGateway.guardar(oferta);
    }

    public List<OfertaIntercambio> buscarOfertasPorIntercambio(Long publicacionId) {
        return ofertaGateway.buscarPorPublicacionId(publicacionId);
    }

    public OfertaIntercambio aceptarOferta(Long ofertaId) {
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Oferta no encontrada"));

        oferta.setEstado("ACEPTADA");
        oferta.setFechaActualizacion(java.time.LocalDateTime.now());
        return ofertaGateway.guardar(oferta);
    }
}