package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.OfertaIntercambio;

import java.util.List;
import java.util.Optional;

public interface OfertaGateway {

    OfertaIntercambio guardar(OfertaIntercambio oferta);

    Optional<OfertaIntercambio> buscarPorId(Long idOferta);

    List<OfertaIntercambio> buscarPorPublicacionId(Long publicacionId);

    List<OfertaIntercambio> buscarPorUsuarioOferente(Long usuarioOferenteId);

    List<OfertaIntercambio> buscarPorEstado(String estado);

    void actualizarEstadoMasivo(List<Long> idsOfertas, String estado);

    // Métodos adicionales si los necesitas
    // List<OfertaIntercambio> buscarOfertasPendientesPorPublicacion(Long publicacionId);
    // List<OfertaIntercambio> buscarOfertasAceptadasPorPublicacion(Long publicacionId);
}