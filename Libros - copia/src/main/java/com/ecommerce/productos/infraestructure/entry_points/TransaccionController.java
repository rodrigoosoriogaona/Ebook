package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.usecase.TransaccionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionUseCase transaccionUseCase;

    @PostMapping("/comprar")
    public ResponseEntity<?> crearTransaccionCompra(@RequestParam Long compradorId,
                                                    @RequestParam Long libroId,
                                                    @RequestParam Integer cantidad) {
        try {
            Transaccion transaccion = transaccionUseCase.crearTransaccionCompra(compradorId, libroId, cantidad);
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
                                                  @RequestParam Long vendedorId) {
        try {
            Transaccion transaccion = transaccionUseCase.confirmarTransaccion(transaccionId, vendedorId);
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
                                                 @RequestParam Long usuarioId) {
        try {
            transaccionUseCase.cancelarTransaccion(transaccionId, usuarioId);
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