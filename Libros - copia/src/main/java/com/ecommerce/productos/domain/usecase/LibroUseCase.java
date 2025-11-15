package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.LibroNoEncontradoException;
import com.ecommerce.productos.domain.exception.UsuarioNoAutorizadoException;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.model.gateway.LibroGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class LibroUseCase {

    private final LibroGateway libroGateway;
    private final UsuarioGateway usuarioGateway;

    public Libro crearLibro(Libro libro) {
        if (!usuarioGateway.usuarioExiste(libro.getUsuarioId())) {
            throw new UsuarioNoAutorizadoException("Usuario no autorizado para crear libro");
        }

        libro.setDisponible(true);
        return libroGateway.guardar(libro);
    }

    public Libro actualizarLibro(Long idLibro, Libro libroActualizado) {
        Libro libroExistente = libroGateway.buscarPorId(idLibro)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado con ID: " + idLibro));

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setDescripcion(libroActualizado.getDescripcion());
        libroExistente.setPrecio(libroActualizado.getPrecio());
        libroExistente.setStock(libroActualizado.getStock());
        libroExistente.setEstado(libroActualizado.getEstado());
        libroExistente.setDisponible(libroActualizado.getDisponible());

        return libroGateway.guardar(libroExistente);
    }

    public Libro consultarLibro(Long idLibro) {
        return libroGateway.buscarPorId(idLibro)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado con ID: " + idLibro));
    }

    public List<Libro> buscarLibrosPorUsuario(Long usuarioId) {
        return libroGateway.buscarPorUsuarioId(usuarioId);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return libroGateway.buscarPorTitulo(titulo);
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return libroGateway.buscarPorAutor(autor);
    }

    public void eliminarLibro(Long idLibro, Long usuarioId) {
        Libro libro = libroGateway.buscarPorId(idLibro)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado con ID: " + idLibro));

        if (!libro.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para eliminar este libro");
        }

        libroGateway.eliminarLibro(idLibro);
    }
}