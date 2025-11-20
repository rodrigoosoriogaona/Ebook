package com.ecommerce.libros.domain.model.Gateway;

import com.ecommerce.libros.domain.model.Publicacion;
import java.util.List;
import java.util.Optional;

public interface PublicacionGateway {
    Publicacion guardar(Publicacion publicacion);
    Optional<Publicacion> buscarPorId(Long id);
    List<Publicacion> buscarPorEstado(String estado);
    List<Publicacion> buscarPorUsuarioId(Long usuarioId);
    // Solo los métodos básicos que SÍ existen en el repository
}