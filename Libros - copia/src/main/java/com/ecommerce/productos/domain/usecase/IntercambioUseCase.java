package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Intercambio;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.model.gateway.IntercambioGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class IntercambioUseCase {

    private final IntercambioGateway intercambioGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;

    public Intercambio crearIntercambio(Long usuarioOfreceId, Long libroOfrecidoId, Long libroSolicitadoId) {
        if (!usuarioGateway.usuarioExiste(usuarioOfreceId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioOfreceId);
        }

        Libro libroOfrecido = libroUseCase.consultarLibro(libroOfrecidoId);
        Libro libroSolicitado = libroUseCase.consultarLibro(libroSolicitadoId);

        // Validaciones
        if (!libroOfrecido.getUsuarioId().equals(usuarioOfreceId)) {
            throw new UsuarioNoAutorizadoException("No eres el dueño del libro ofrecido");
        }

        if (!libroOfrecido.getDisponible() || !libroSolicitado.getDisponible()) {
            throw new LibroNoDisponibleException("Uno o ambos libros no están disponibles para intercambio");
        }

        if (libroOfrecido.getUsuarioId().equals(libroSolicitado.getUsuarioId())) {
            throw new IntercambioNoValidoException("No puedes intercambiar libros del mismo usuario");
        }

        Intercambio intercambio = new Intercambio();
        intercambio.setLibroOfrecidoId(libroOfrecidoId);
        intercambio.setLibroSolicitadoId(libroSolicitadoId);
        intercambio.setUsuarioOfreceId(usuarioOfreceId);
        intercambio.setUsuarioSolicitaId(libroSolicitado.getUsuarioId());
        intercambio.setEstado("OFERTADO");
        intercambio.setFechaCreacion(LocalDateTime.now());
        intercambio.setFechaActualizacion(LocalDateTime.now());

        return intercambioGateway.guardar(intercambio);
    }

    public Intercambio aceptarIntercambio(Long intercambioId, Long usuarioSolicitaId) {
        Intercambio intercambio = intercambioGateway.buscarPorId(intercambioId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Intercambio no encontrado"));

        if (!intercambio.getUsuarioSolicitaId().equals(usuarioSolicitaId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para aceptar este intercambio");
        }

        intercambio.setEstado("ACEPTADO");
        intercambio.setFechaActualizacion(LocalDateTime.now());

        // Marcar libros como no disponibles
        Libro libroOfrecido = libroUseCase.consultarLibro(intercambio.getLibroOfrecidoId());
        Libro libroSolicitado = libroUseCase.consultarLibro(intercambio.getLibroSolicitadoId());

        libroOfrecido.setDisponible(false);
        libroSolicitado.setDisponible(false);

        libroUseCase.actualizarLibro(libroOfrecido.getIdLibro(), libroOfrecido);
        libroUseCase.actualizarLibro(libroSolicitado.getIdLibro(), libroSolicitado);

        return intercambioGateway.guardar(intercambio);
    }

    public Intercambio rechazarIntercambio(Long intercambioId, Long usuarioId) {
        Intercambio intercambio = intercambioGateway.buscarPorId(intercambioId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Intercambio no encontrado"));

        if (!intercambio.getUsuarioSolicitaId().equals(usuarioId) &&
                !intercambio.getUsuarioOfreceId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para rechazar este intercambio");
        }

        intercambio.setEstado("RECHAZADO");
        intercambio.setFechaActualizacion(LocalDateTime.now());

        return intercambioGateway.guardar(intercambio);
    }

    public Intercambio consultarIntercambio(Long intercambioId) {
        return intercambioGateway.buscarPorId(intercambioId)
                .orElseThrow(() -> new IntercambioNoEncontradoException("Intercambio no encontrado con ID: " + intercambioId));
    }


}