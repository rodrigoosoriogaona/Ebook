package com.ecommerce.libros.domain.UseCase;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.domain.model.Gateway.PublicacionGateway;
import com.ecommerce.libros.domain.model.Gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PublicacionUseCase {

    private final PublicacionGateway publicacionGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;

    public Publicacion crearPublicacionVenta(Long usuarioId, Long libroId, Double precio) {
        return crearPublicacion(usuarioId, libroId, "VENTA", precio, null);
    }

    public Publicacion crearPublicacionIntercambio(Long usuarioId, Long libroId, String condiciones) {
        return crearPublicacion(usuarioId, libroId, "INTERCAMBIO", null, condiciones);
    }

    private Publicacion crearPublicacion(Long usuarioId, Long libroId, String tipo, Double precio, String condiciones) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }

        Libro libro = libroUseCase.consultarLibro(libroId);

        if (!libro.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No eres el dueño del libro");
        }

        if (!libro.getDisponible()) {
            throw new LibroNoDisponibleException("El libro no está disponible");
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setUsuarioId(usuarioId);
        publicacion.setLibroId(libroId);
        publicacion.setTipo(tipo);
        publicacion.setEstado("ACTIVA");
        publicacion.setPrecioVenta(precio);
        publicacion.setCondicionesIntercambio(condiciones);
        publicacion.setFechaCreacion(LocalDateTime.now());
        publicacion.setFechaActualizacion(LocalDateTime.now());

        return publicacionGateway.guardar(publicacion);
    }

    public List<Publicacion> consultarPublicacionesActivas() {
        return publicacionGateway.buscarPorEstado("ACTIVA");
    }

    public List<Publicacion> consultarPublicacionesPorUsuario(Long usuarioId) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        return publicacionGateway.buscarPorUsuarioId(usuarioId);
    }

    public Publicacion cerrarPublicacion(Long publicacionId, Long usuarioId) {
        Publicacion publicacion = publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!publicacion.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para cerrar esta publicación");
        }

        publicacion.setEstado("CERRADA");
        publicacion.setFechaActualizacion(LocalDateTime.now());

        return publicacionGateway.guardar(publicacion);
    }
}