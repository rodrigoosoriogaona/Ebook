package com.ecommerce.libros.Infraestructure.entry_points;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Transaccion;
import com.ecommerce.libros.domain.UseCase.TransaccionUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TransaccionController {

    private final TransaccionUseCase transaccionUseCase;

    @Data
    public static class CrearTransaccionRequest {
        private Long publicacionId;
        private String tipoPago; // VIRTUAL, PRESENCIAL
        private String metodoPago; // TARJETA, EFECTIVO, TRANSFERENCIA
    }

    @Data
    public static class AceptarRechazarTransaccionRequest {
        private Long usuarioId;
    }

    @PostMapping("/comprar/{usuarioCompradorId}")
    public ResponseEntity<?> crearTransaccion(@PathVariable Long usuarioCompradorId,
                                              @RequestBody CrearTransaccionRequest request) {
        try {
            Transaccion transaccion = transaccionUseCase.crearTransaccionVenta(
                    request.getPublicacionId(),
                    usuarioCompradorId,
                    request.getTipoPago(),
                    request.getMetodoPago()
            );
            return ResponseEntity.ok(transaccion);
        } catch (PublicacionNoEncontradaException | PublicacionNoActivaException |
                 UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                 LibroNoDisponibleException | TransaccionNoValidaException |
                 PagoFallidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear transacción: " + e.getMessage());
        }
    }

    @PutMapping("/{transaccionId}/aceptar")
    public ResponseEntity<?> aceptarTransaccion(@PathVariable Long transaccionId,
                                                @RequestBody AceptarRechazarTransaccionRequest request) {
        try {
            Transaccion transaccion = transaccionUseCase.aceptarTransaccion(transaccionId, request.getUsuarioId());
            return ResponseEntity.ok(transaccion);
        } catch (TransaccionNoEncontradaException | PublicacionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException | TransaccionNoValidaException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aceptar transacción: " + e.getMessage());
        }
    }

    @PutMapping("/{transaccionId}/rechazar")
    public ResponseEntity<?> rechazarTransaccion(@PathVariable Long transaccionId,
                                                 @RequestBody AceptarRechazarTransaccionRequest request) {
        try {
            Transaccion transaccion = transaccionUseCase.rechazarTransaccion(transaccionId, request.getUsuarioId());
            return ResponseEntity.ok(transaccion);
        } catch (TransaccionNoEncontradaException | PublicacionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al rechazar transacción: " + e.getMessage());
        }
    }

    @GetMapping("/compras/{usuarioId}")
    public ResponseEntity<?> consultarComprasPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Transaccion> transacciones = transaccionUseCase.consultarTransaccionesPorUsuario(usuarioId);
            return ResponseEntity.ok(transacciones);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar compras: " + e.getMessage());
        }
    }


}