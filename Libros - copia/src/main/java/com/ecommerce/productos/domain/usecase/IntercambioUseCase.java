package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.domain.model.PublicacionIntercambio;
import com.ecommerce.productos.domain.model.gateway.OfertaGateway;
import com.ecommerce.productos.domain.model.gateway.PublicacionIntercambioGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IntercambioUseCase {

    private final PublicacionIntercambioGateway publicacionGateway;
    private final OfertaGateway ofertaGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;

    // ========== CREAR PUBLICACIÓN ==========
    public PublicacionIntercambio crearPublicacion(Long usuarioPropietarioId, Long libroOfrecidoId, String descripcion) {
        // Validar que el usuario existe
        if (!usuarioGateway.usuarioExiste(usuarioPropietarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioPropietarioId);
        }

        // Validar que el libro existe y pertenece al usuario
        Libro libro = libroUseCase.consultarLibro(libroOfrecidoId);
        if (!libro.getUsuarioId().equals(usuarioPropietarioId)) {
            throw new UsuarioNoAutorizadoException("El libro no pertenece al usuario");
        }

        if (!libro.getDisponible()) {
            throw new LibroNoDisponibleException("El libro no está disponible para intercambio");
        }

        // Crear publicación
        PublicacionIntercambio publicacion = new PublicacionIntercambio();
        publicacion.setLibroOfrecidoId(libroOfrecidoId);
        publicacion.setUsuarioPropietarioId(usuarioPropietarioId);
        publicacion.setEstado("ACTIVA");
        publicacion.setDescripcion(descripcion);
        publicacion.setFechaCreacion(LocalDateTime.now());
        publicacion.setFechaActualizacion(LocalDateTime.now());

        return publicacionGateway.guardar(publicacion);
    }

    // ========== CREAR OFERTA ==========
    public OfertaIntercambio crearOferta(Long publicacionId, Long usuarioOferenteId, Long libroOfrecidoId, String mensaje) {
        // Validar que la publicación existe y está activa
        PublicacionIntercambio publicacion = publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Publicación no encontrada"));

        if (!"ACTIVA".equals(publicacion.getEstado())) {
            throw new IntercambioNoValidoException("La publicación no está activa");
        }

        // Validar que el usuario no es el dueño de la publicación
        if (publicacion.getUsuarioPropietarioId().equals(usuarioOferenteId)) {
            throw new IntercambioNoValidoException("No puedes ofertar en tu propia publicación");
        }

        // Validar que el libro ofrecido existe y pertenece al usuario oferente
        Libro libroOfrecido = libroUseCase.consultarLibro(libroOfrecidoId);
        if (!libroOfrecido.getUsuarioId().equals(usuarioOferenteId)) {
            throw new UsuarioNoAutorizadoException("El libro ofrecido no pertenece al usuario");
        }

        if (!libroOfrecido.getDisponible()) {
            throw new LibroNoDisponibleException("El libro ofrecido no está disponible");
        }

        // Crear oferta
        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setPublicacionId(publicacionId);
        oferta.setUsuarioOferenteId(usuarioOferenteId);
        oferta.setLibroOfrecidoId(libroOfrecidoId);
        oferta.setMensaje(mensaje);
        oferta.setEstado("PENDIENTE");
        oferta.setFechaCreacion(LocalDateTime.now());
        oferta.setFechaActualizacion(LocalDateTime.now());

        return ofertaGateway.guardar(oferta);
    }

    // ========== ACEPTAR OFERTA ==========
    public OfertaIntercambio aceptarOferta(Long ofertaId, Long usuarioPropietarioId) {
        // Buscar la oferta
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Oferta no encontrada"));

        // Buscar la publicación
        PublicacionIntercambio publicacion = publicacionGateway.buscarPorId(oferta.getPublicacionId())
                .orElseThrow(() -> new IntercambioNoEncontradoException("Publicación no encontrada"));

        // Validar que el usuario es el dueño de la publicación
        if (!publicacion.getUsuarioPropietarioId().equals(usuarioPropietarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el dueño puede aceptar ofertas");
        }

        // Validar que la publicación está activa
        if (!"ACTIVA".equals(publicacion.getEstado())) {
            throw new IntercambioNoValidoException("La publicación no está activa");
        }

        // Validar que la oferta está pendiente
        if (!"PENDIENTE".equals(oferta.getEstado())) {
            throw new IntercambioNoValidoException("La oferta no está pendiente");
        }

        // Aceptar la oferta
        oferta.setEstado("ACEPTADA");
        oferta.setFechaActualizacion(LocalDateTime.now());
        ofertaGateway.guardar(oferta);

        // Cerrar la publicación
        publicacion.setEstado("CERRADA");
        publicacion.setFechaActualizacion(LocalDateTime.now());
        publicacionGateway.guardar(publicacion);

        // Rechazar todas las demás ofertas pendientes
        List<OfertaIntercambio> todasLasOfertas = ofertaGateway.buscarPorPublicacionId(publicacion.getIdPublicacion());
        List<Long> idsOfertasPendientes = todasLasOfertas.stream()
                .filter(o -> "PENDIENTE".equals(o.getEstado()) && !o.getIdOferta().equals(ofertaId))
                .map(OfertaIntercambio::getIdOferta)
                .collect(Collectors.toList());

        if (!idsOfertasPendientes.isEmpty()) {
            ofertaGateway.actualizarEstadoMasivo(idsOfertasPendientes, "RECHAZADA");
        }

        // OPCIONAL: Marcar libros como "No disponible" o "Intercambiado"
        Libro libroPublicacion = libroUseCase.consultarLibro(publicacion.getLibroOfrecidoId());
        libroPublicacion.setDisponible(false);
        libroPublicacion.setEstado("INTERCAMBIADO");
        libroUseCase.actualizarLibro(libroPublicacion.getIdLibro(), libroPublicacion);

        Libro libroOferta = libroUseCase.consultarLibro(oferta.getLibroOfrecidoId());
        libroOferta.setDisponible(false);
        libroOferta.setEstado("INTERCAMBIADO");
        libroUseCase.actualizarLibro(libroOferta.getIdLibro(), libroOferta);

        return oferta;
    }

    // ========== RECHAZAR OFERTA ==========
    public OfertaIntercambio rechazarOferta(Long ofertaId, Long usuarioPropietarioId) {
        // Buscar la oferta
        OfertaIntercambio oferta = ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Oferta no encontrada"));

        // Buscar la publicación
        PublicacionIntercambio publicacion = publicacionGateway.buscarPorId(oferta.getPublicacionId())
                .orElseThrow(() -> new IntercambioNoEncontradoException("Publicación no encontrada"));

        // Validar que el usuario es el dueño de la publicación
        if (!publicacion.getUsuarioPropietarioId().equals(usuarioPropietarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el dueño puede rechazar ofertas");
        }

        // Validar que la oferta está pendiente
        if (!"PENDIENTE".equals(oferta.getEstado())) {
            throw new IntercambioNoValidoException("La oferta no está pendiente");
        }

        // Rechazar la oferta
        oferta.setEstado("RECHAZADA");
        oferta.setFechaActualizacion(LocalDateTime.now());

        return ofertaGateway.guardar(oferta);
    }

    // ========== CONSULTAR PUBLICACIONES ==========
    public List<PublicacionIntercambio> listarPublicacionesActivas() {
        return publicacionGateway.buscarActivas();
    }

    public List<PublicacionIntercambio> listarMisPublicaciones(Long usuarioId) {
        return publicacionGateway.buscarPorUsuario(usuarioId);
    }

    public PublicacionIntercambio consultarPublicacion(Long publicacionId) {
        return publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Publicación no encontrada"));
    }

    // ========== CONSULTAR OFERTAS ==========
    public List<OfertaIntercambio> listarOfertasDePublicacion(Long publicacionId) {
        return ofertaGateway.buscarPorPublicacionId(publicacionId);
    }

    public List<OfertaIntercambio> listarMisOfertas(Long usuarioId) {
        return ofertaGateway.buscarPorUsuarioOferente(usuarioId);
    }

    public OfertaIntercambio consultarOferta(Long ofertaId) {
        return ofertaGateway.buscarPorId(ofertaId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Oferta no encontrada"));
    }

    public void consultarIntercambio(Long intercambioId) {
    }
}

