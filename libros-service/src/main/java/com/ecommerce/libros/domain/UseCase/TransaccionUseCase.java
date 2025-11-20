package com.ecommerce.libros.domain.UseCase;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.domain.model.Transaccion;
import com.ecommerce.libros.domain.model.Gateway.PagoGateway;
import com.ecommerce.libros.domain.model.Gateway.PublicacionGateway;
import com.ecommerce.libros.domain.model.Gateway.TransaccionGateway;
import com.ecommerce.libros.domain.model.Gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TransaccionUseCase {

    private final TransaccionGateway transaccionGateway;
    private final PublicacionGateway publicacionGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;
    private final PagoGateway pagoGateway;

    public Transaccion crearTransaccionVenta(Long publicacionId, Long usuarioCompradorId, String tipoPago, String metodoPago) {
        Publicacion publicacion = publicacionGateway.buscarPorId(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!"ACTIVA".equals(publicacion.getEstado())) {
            throw new PublicacionNoActivaException("La publicación no está activa");
        }

        if (!"VENTA".equals(publicacion.getTipo())) {
            throw new TransaccionNoValidaException("Solo se pueden comprar publicaciones de venta");
        }

        if (publicacion.getUsuarioId().equals(usuarioCompradorId)) {
            throw new UsuarioNoAutorizadoException("No puedes comprar tu propia publicación");
        }

        if (!usuarioGateway.usuarioExiste(usuarioCompradorId)) {
            throw new UsuarioNoEncontradoException("Usuario comprador no encontrado");
        }

        Libro libro = libroUseCase.consultarLibro(publicacion.getLibroId());
        if (!libro.getDisponible()) {
            throw new LibroNoDisponibleException("El libro no está disponible");
        }

        // Simular procesamiento de pago
        boolean pagoExitoso = pagoGateway.procesarPago(usuarioCompradorId, publicacion.getPrecioVenta(), metodoPago);
        if (!pagoExitoso) {
            throw new PagoFallidoException("El pago no pudo ser procesado");
        }

        Transaccion transaccion = new Transaccion();
        transaccion.setPublicacionId(publicacionId);
        transaccion.setUsuarioCompradorId(usuarioCompradorId);
        transaccion.setTipoPago(tipoPago);
        transaccion.setMetodoPago(metodoPago);
        transaccion.setMonto(publicacion.getPrecioVenta());
        transaccion.setEstado("PENDIENTE");
        transaccion.setFechaCreacion(LocalDateTime.now());
        transaccion.setFechaActualizacion(LocalDateTime.now());

        return transaccionGateway.guardar(transaccion);
    }

    public Transaccion aceptarTransaccion(Long transaccionId, Long usuarioId) {
        Transaccion transaccion = transaccionGateway.buscarPorId(transaccionId)
                .orElseThrow(() -> new TransaccionNoEncontradaException("Transacción no encontrada"));

        Publicacion publicacion = publicacionGateway.buscarPorId(transaccion.getPublicacionId())
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!publicacion.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el vendedor puede aceptar la transacción");
        }

        if (!"PENDIENTE".equals(transaccion.getEstado())) {
            throw new TransaccionNoValidaException("La transacción no está en estado PENDIENTE");
        }

        // Completar transacción
        transaccion.setEstado("COMPLETADA");
        transaccion.setFechaActualizacion(LocalDateTime.now());

        // Cerrar publicación
        publicacion.setEstado("CERRADA");
        publicacion.setFechaActualizacion(LocalDateTime.now());

        // Marcar libro como no disponible
        Libro libro = libroUseCase.consultarLibro(publicacion.getLibroId());
        libro.setDisponible(false);
        libroUseCase.actualizarLibro(libro.getIdLibro(), libro, publicacion.getUsuarioId()); // CORREGIDO: usar publicacion.getUsuarioId()

        publicacionGateway.guardar(publicacion);
        return transaccionGateway.guardar(transaccion);
    }

    public Transaccion rechazarTransaccion(Long transaccionId, Long usuarioId) {
        Transaccion transaccion = transaccionGateway.buscarPorId(transaccionId)
                .orElseThrow(() -> new TransaccionNoEncontradaException("Transacción no encontrada"));

        Publicacion publicacion = publicacionGateway.buscarPorId(transaccion.getPublicacionId())
                .orElseThrow(() -> new PublicacionNoEncontradaException("Publicación no encontrada"));

        if (!publicacion.getUsuarioId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("Solo el vendedor puede rechazar la transacción");
        }

        // Reembolsar pago (simulado) - CORREGIDO: usar reversarPago
        pagoGateway.reversarPago(transaccion.getUsuarioCompradorId(), transaccion.getMonto());

        transaccion.setEstado("RECHAZADA");
        transaccion.setFechaActualizacion(LocalDateTime.now());

        return transaccionGateway.guardar(transaccion);
    }

    public List<Transaccion> consultarTransaccionesPorUsuario(Long usuarioId) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        return transaccionGateway.buscarPorUsuarioCompradorId(usuarioId);
    }
}