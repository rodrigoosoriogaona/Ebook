package com.ecommerce.libros.domain.model.Gateway;

import com.ecommerce.libros.domain.model.Transaccion;
import java.util.List;
import java.util.Optional;

public interface TransaccionGateway {
    Transaccion guardar(Transaccion transaccion);
    Optional<Transaccion> buscarPorId(Long id);
    List<Transaccion> buscarPorUsuarioCompradorId(Long usuarioId);
    List<Transaccion> buscarPorPublicacionId(Long publicacionId);
}