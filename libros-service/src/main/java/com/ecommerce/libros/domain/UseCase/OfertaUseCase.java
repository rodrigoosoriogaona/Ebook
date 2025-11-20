package com.ecommerce.libros.domain.UseCase;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.model.OfertaIntercambio;
import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.domain.model.Gateway.OfertaGateway;
import com.ecommerce.libros.domain.model.Gateway.PublicacionGateway;
import com.ecommerce.libros.domain.model.Gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OfertaUseCase {

    private final OfertaGateway ofertaGateway;
    private final PublicacionGateway publicacionGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;

    public OfertaIntercambio crearOfertaIntercambio(Long publicacionId, Long usuarioOfertanteId, Long libroOfertadoId, String mensaje) {
        Publicacion publicacion = publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!"ACTIVA".equals(publicacion.getEstado())) {
            throw new PublicacionNoActivaException("La publicación no está activa");
        }

        if (!"INTERCAMBIO".equals(publicacion.getTipo())) {
            throw new TransaccionNoValidaException("Solo se pueden hacer ofertas en publicaciones de intercambio");
        }

        if (publicacion.getUsuarioId().equals(usuarioOfertanteId)) {
            throw new UsuarioNoAutorizadoException("No puedes hacer una oferta en tu propia publicación");
        }

        if (!usuarioGateway.usuarioExiste(usuarioOfertanteId)) {
            throw new UsuarioNoEncontradoException("Usuario ofertante no encontrado");
        }

        Libro libroOfertado = libroUseCase.consultarLibro(libroOfertadoId);

        if (!libroOfertado.getUsuarioId().equals(usuarioOfertanteId)) {
            throw new UsuarioNoAutorizadoException("No eres el dueño del libro ofertado");
        }

        if (!libroOfertado.getDisponible()) {
            throw new LibroNoDisponibleException("El libro ofertado no está disponible");
        }

        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setPublicacionId(publicacionId);
        oferta.setUsuarioOfertanteId(usuarioOfertanteId);
        oferta.setLibroOfertadoId(libroOfertadoId);
        oferta.setMensaje(mensaje);
        oferta.setEstado("PENDIENTE");
        oferta.setFechaCreacion(LocalDateTime.now());
        oferta.setFechaActualizacion(LocalDateTime.now());

        return ofertaGateway.guardar(oferta);
    }

    public OfertaIntercambio aceptarOferta(Long ofertaId, Long usuarioId) {
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new OfertaNoEncontradaException("Oferta no encontrada"));

        Publicacion publicacion = publicacionGateway.buscarPorId(oferta.getPublicacionId())
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!publicacion.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el dueño de la publicación puede aceptar ofertas");
        }

        if (!"PENDIENTE".equals(oferta.getEstado())) {
            throw new TransaccionNoValidaException("La oferta no está en estado PENDIENTE");
        }

        // Aceptar la oferta
        oferta.setEstado("ACEPTADA");
        oferta.setFechaActualizacion(LocalDateTime.now());

        // Cerrar la publicación
        publicacion.setEstado("CERRADA");
        publicacion.setFechaActualizacion(LocalDateTime.now());

        // Rechazar otras ofertas pendientes
        List<OfertaIntercambio> otrasOfertas = ofertaGateway.buscarPorPublicacionId(publicacion.getIdPublicacion());
        for (OfertaIntercambio otraOferta : otrasOfertas) {
            if (!otraOferta.getIdOferta().equals(ofertaId) && "PENDIENTE".equals(otraOferta.getEstado())) {
                otraOferta.setEstado("RECHAZADA");
                otraOferta.setFechaActualizacion(LocalDateTime.now());
                ofertaGateway.guardar(otraOferta);
            }
        }

        // Actualizar estado de los libros
        Libro libroPublicacion = libroUseCase.consultarLibro(publicacion.getLibroId());
        Libro libroOfertado = libroUseCase.consultarLibro(oferta.getLibroOfertadoId());

        libroPublicacion.setDisponible(false);
        libroOfertado.setDisponible(false);

        libroUseCase.actualizarLibro(libroPublicacion.getIdLibro(), libroPublicacion, libroPublicacion.getUsuarioId());
        libroUseCase.actualizarLibro(libroOfertado.getIdLibro(), libroOfertado, libroOfertado.getUsuarioId());

        publicacionGateway.guardar(publicacion);
        return ofertaGateway.guardar(oferta);
    }

    public OfertaIntercambio rechazarOferta(Long ofertaId, Long usuarioId) {
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new OfertaNoEncontradaException("Oferta no encontrada"));

        Publicacion publicacion = publicacionGateway.buscarPorId(oferta.getPublicacionId())
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!publicacion.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el dueño de la publicación puede rechazar ofertas");
        }

        oferta.setEstado("RECHAZADA");
        oferta.setFechaActualizacion(LocalDateTime.now());

        return ofertaGateway.guardar(oferta);
    }

    public List<OfertaIntercambio> consultarOfertasPorPublicacion(Long publicacionId) {
        publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));
        return ofertaGateway.buscarPorPublicacionId(publicacionId);
    }

    public List<OfertaIntercambio> consultarOfertasPorUsuario(Long usuarioId) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        return ofertaGateway.buscarPorUsuarioOfertanteId(usuarioId);
    }
}