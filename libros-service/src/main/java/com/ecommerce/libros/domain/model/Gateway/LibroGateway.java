package com.ecommerce.libros.domain.model.Gateway;

import com.ecommerce.libros.domain.model.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroGateway {
    Libro guardar(Libro libro);
    Optional<Libro> buscarPorId(Long id);
    List<Libro> buscarPorUsuarioId(Long usuarioId);
    List<Libro> buscarPorDisponibilidad(Boolean disponible);
    void eliminar(Long libroId);
}