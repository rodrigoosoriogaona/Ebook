
package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.PublicacionIntercambio;
import java.util.List;
import java.util.Optional;

public interface PublicacionIntercambioGateway {
    PublicacionIntercambio guardar(PublicacionIntercambio publicacion);
    Optional<PublicacionIntercambio> buscarPorId(Long idPublicacion);
    List<PublicacionIntercambio> buscarTodas();
    List<PublicacionIntercambio> buscarActivas();
    List<PublicacionIntercambio> buscarPorUsuario(Long usuarioId);
    boolean existePublicacion(Long idPublicacion);
}