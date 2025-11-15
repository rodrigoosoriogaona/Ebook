package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.model.gateway.PagoGateway;
import com.ecommerce.productos.domain.model.gateway.TransaccionGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransaccionUseCase {

    private final TransaccionGateway transaccionGateway;
    private final LibroUseCase libroUseCase;
    private final UsuarioGateway usuarioGateway;
    private final PagoGateway pagoGateway;

    // Método que falta agregar
    public Transaccion consultarTransaccion(Long transaccionId) {
        return transaccionGateway.buscarPorId(transaccionId)
                .orElseThrow(() -> new TransaccionNoEncontradaException("Transacción no encontrada con ID: " + transaccionId));
    }

    public Transaccion crearTransaccionCompra(Long compradorId, Long libroId, Integer cantidad) {
        if (!usuarioGateway.usuarioExiste(compradorId)) {
            throw new UsuarioNoEncontradoException("Comprador no encontrado con ID: " + compradorId);
        }

        Libro libro = libroUseCase.consultarLibro(libroId);

        if (!libro.getDisponible()) {
            throw new LibroNoDisponibleException("El libro no está disponible para la venta");
        }

        if (libro.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente para el libro: " + libro.getTitulo());
        }

        if (libro.getUsuarioId().equals(compradorId)) {
            throw new TransaccionNoValidaException("No puedes comprar tu propio libro");
        }

        // Procesar pago
        Double montoTotal = libro.getPrecio() * cantidad;
        if (!pagoGateway.procesarPago(compradorId, montoTotal)) {
            throw new PagoFallidoException("Error al procesar el pago");
        }

        // Crear transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setLibroId(libroId);
        transaccion.setCompradorId(compradorId);
        transaccion.setVendedorId(libro.getUsuarioId());
        transaccion.setPrecio(libro.getPrecio());
        transaccion.setCantidad(cantidad);
        transaccion.setEstado("PENDIENTE");
        transaccion.setFechaCreacion(LocalDateTime.now());
        transaccion.setFechaActualizacion(LocalDateTime.now());

        return transaccionGateway.guardar(transaccion);
    }

    public Transaccion confirmarTransaccion(Long transaccionId, Long vendedorId) {
        Transaccion transaccion = consultarTransaccion(transaccionId);

        if (!transaccion.getVendedorId().equals(vendedorId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para confirmar esta transacción");
        }

        transaccion.setEstado("CONFIRMADA");
        transaccion.setFechaActualizacion(LocalDateTime.now());

        // Actualizar stock del libro
        Libro libro = libroUseCase.consultarLibro(transaccion.getLibroId());
        libro.setStock(libro.getStock() - transaccion.getCantidad());
        libroUseCase.actualizarLibro(libro.getIdLibro(), libro);

        return transaccionGateway.guardar(transaccion);
    }

    public void cancelarTransaccion(Long transaccionId, Long usuarioId) {
        Transaccion transaccion = consultarTransaccion(transaccionId);

        if (!transaccion.getCompradorId().equals(usuarioId) && !transaccion.getVendedorId().equals(usuarioId)) {
            throw new UsuarioNoAutorizadoException("No autorizado para cancelar esta transacción");
        }

        // Revertir pago si es el comprador quien cancela
        if (transaccion.getCompradorId().equals(usuarioId)) {
            pagoGateway.revertirPago(transaccionId.toString());
        }

        transaccion.setEstado("CANCELADA");
        transaccion.setFechaActualizacion(LocalDateTime.now());
        transaccionGateway.guardar(transaccion);
    }
}