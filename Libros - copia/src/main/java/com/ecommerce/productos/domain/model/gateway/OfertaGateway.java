package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.OfertaIntercambio;
import java.util.List;
import java.util.Optional;

public interface OfertaGateway {
    OfertaIntercambio guardar(OfertaIntercambio oferta);
    Optional<OfertaIntercambio> buscarPorId(Long idOferta);
    List<OfertaIntercambio> buscarPorIntercambioId(Long intercambioId);
}