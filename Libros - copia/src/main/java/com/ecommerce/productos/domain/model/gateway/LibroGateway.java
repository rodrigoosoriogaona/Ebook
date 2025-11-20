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
    List<Libro> buscarTodos(); // NUEVO: Listar todos los libros
    List<Libro> buscarDisponibles(); // NUEVO: Listar solo disponibles
    void eliminarLibro(Long idLibro);
    boolean existeLibro(Long idLibro);
}