package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Intercambio;
import java.util.List;
import java.util.Optional;

public interface IntercambioGateway {
    Intercambio guardar(Intercambio intercambio);
    Optional<Intercambio> buscarPorId(Long idIntercambio);
    List<Intercambio> buscarPorUsuarioOfreceId(Long usuarioId);
    List<Intercambio> buscarPorUsuarioSolicitaId(Long usuarioId);
    boolean existeIntercambio(Long idIntercambio);
}