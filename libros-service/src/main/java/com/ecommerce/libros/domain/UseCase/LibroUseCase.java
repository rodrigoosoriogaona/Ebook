package com.ecommerce.libros.domain.UseCase;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.model.Gateway.LibroGateway;
import com.ecommerce.libros.domain.model.Gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class LibroUseCase {

    private final LibroGateway libroGateway;
    private final UsuarioGateway usuarioGateway;

    public Libro crearLibro(Libro libro, Long usuarioId) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }

        libro.setUsuarioId(usuarioId);
        libro.setDisponible(true);
        libro.setFechaCreacion(LocalDateTime.now());
        libro.setFechaActualizacion(LocalDateTime.now());

        return libroGateway.guardar(libro);
    }

    public Libro actualizarLibro(Long libroId, Libro libroActualizado, Long usuarioId) {
        Libro libroExistente = consultarLibro(libroId);

        if (!libroExistente.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No eres el dueño del libro");
        }

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setGenero(libroActualizado.getGenero());
        libroExistente.setSinopsis(libroActualizado.getSinopsis());
        libroExistente.setEstadoFisico(libroActualizado.getEstadoFisico());
        libroExistente.setPrecio(libroActualizado.getPrecio());
        libroExistente.setEnVenta(libroActualizado.getEnVenta());
        libroExistente.setEnIntercambio(libroActualizado.getEnIntercambio());
        libroExistente.setFechaActualizacion(LocalDateTime.now());

        return libroGateway.guardar(libroExistente);
    }

    public List<Libro> consultarBibliotecaUsuario(Long usuarioId) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        return libroGateway.buscarPorUsuarioId(usuarioId);
    }

    public Libro consultarLibro(Long libroId) {
        return libroGateway.buscarPorId(libroId)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado"));
    }

    public void eliminarLibro(Long libroId, Long usuarioId) {
        Libro libro = consultarLibro(libroId);

        if (!libro.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para eliminar este libro");
        }

        libroGateway.eliminar(libroId);
    }
}