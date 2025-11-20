package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.usecase.TransaccionUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionUseCase transaccionUseCase;

    @Data
    public static class CrearTransaccionRequest {
        private Long compradorId;
        private Long libroId;
        private Integer cantidad;
    }

    @Data
    public static class ConfirmarCancelarTransaccionRequest {
        private Long usuarioId;
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> crearTransaccionCompra(@RequestBody CrearTransaccionRequest request) {
        try {
            Transaccion transaccion = transaccionUseCase.crearTransaccionCompra(
                    request.getCompradorId(),
                    request.getLibroId(),
                    request.getCantidad()
            );
            return ResponseEntity.ok(transaccion);
        } catch (UsuarioNoEncontradoException | LibroNoDisponibleException |
                 StockInsuficienteException | TransaccionNoValidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (PagoFallidoException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear transacción: " + e.getMessage());
        }
    }

    @PutMapping("/confirmar/{transaccionId}")
    public ResponseEntity<?> confirmarTransaccion(@PathVariable Long transaccionId,
                                                  @RequestBody ConfirmarCancelarTransaccionRequest request) {
        try {
            Transaccion transaccion = transaccionUseCase.confirmarTransaccion(transaccionId, request.getUsuarioId());
            return ResponseEntity.ok(transaccion);
        } catch (TransaccionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al confirmar transacción: " + e.getMessage());
        }
    }

    @PutMapping("/cancelar/{transaccionId}")
    public ResponseEntity<?> cancelarTransaccion(@PathVariable Long transaccionId,
                                                 @RequestBody ConfirmarCancelarTransaccionRequest request) {
        try {
            transaccionUseCase.cancelarTransaccion(transaccionId, request.getUsuarioId());
            return ResponseEntity.ok("Transacción cancelada correctamente");
        } catch (TransaccionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar transacción: " + e.getMessage());
        }
    }
}