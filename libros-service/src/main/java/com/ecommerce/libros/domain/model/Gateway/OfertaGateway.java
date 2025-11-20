package com.ecommerce.libros.domain.model.Gateway;

import com.ecommerce.libros.domain.model.OfertaIntercambio;
import java.util.List;
import java.util.Optional;

public interface OfertaGateway {
    OfertaIntercambio guardar(OfertaIntercambio oferta);
    Optional<OfertaIntercambio> buscarPorId(Long id);
    List<OfertaIntercambio> buscarPorPublicacionId(Long publicacionId);
    List<OfertaIntercambio> buscarPorUsuarioOfertanteId(Long usuarioId);

}