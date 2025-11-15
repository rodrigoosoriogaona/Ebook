package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroGateway {
    Libro guardar(Libro libro);
    Optional<Libro> buscarPorId(Long idLibro);
    List<Libro> buscarPorUsuarioId(Long usuarioId);
    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> buscarPorAutor(String autor);
    void eliminarLibro(Long idLibro);
    boolean existeLibro(Long idLibro);
}