package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Intercambio;
import com.ecommerce.productos.domain.usecase.IntercambioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/intercambios")
@RequiredArgsConstructor
public class IntercambioController {

    private final IntercambioUseCase intercambioUseCase;

    @PostMapping("/crear")
    public ResponseEntity<?> crearIntercambio(@RequestParam Long usuarioOfreceId,
                                              @RequestParam Long libroOfrecidoId,
                                              @RequestParam Long libroSolicitadoId) {
        try {
            Intercambio intercambio = intercambioUseCase.crearIntercambio(usuarioOfreceId, libroOfrecidoId, libroSolicitadoId);
            return ResponseEntity.ok(intercambio);
        } catch (UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                 LibroNoDisponibleException | IntercambioNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear intercambio: " + e.getMessage());
        }
    }

    @PutMapping("/aceptar/{intercambioId}")
    public ResponseEntity<?> aceptarIntercambio(@PathVariable Long intercambioId,
                                                @RequestParam Long usuarioSolicitaId) {
        try {
            Intercambio intercambio = intercambioUseCase.aceptarIntercambio(intercambioId, usuarioSolicitaId);
            return ResponseEntity.ok(intercambio);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aceptar intercambio: " + e.getMessage());
        }
    }

    @PutMapping("/rechazar/{intercambioId}")
    public ResponseEntity<?> rechazarIntercambio(@PathVariable Long intercambioId,
                                                 @RequestParam Long usuarioId) {
        try {
            Intercambio intercambio = intercambioUseCase.rechazarIntercambio(intercambioId, usuarioId);
            return ResponseEntity.ok(intercambio);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al rechazar intercambio: " + e.getMessage());
        }
    }
}