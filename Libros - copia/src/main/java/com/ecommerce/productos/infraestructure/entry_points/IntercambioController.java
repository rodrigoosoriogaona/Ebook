package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Intercambio;
import com.ecommerce.productos.domain.usecase.IntercambioUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/intercambios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class IntercambioController {

    private final IntercambioUseCase intercambioUseCase;

    @Data
    public static class CrearIntercambioRequest {
        private Long usuarioOfreceId;
        private Long libroOfrecidoId;
        private Long libroSolicitadoId;
    }

    @Data
    public static class AceptarRechazarIntercambioRequest {
        private Long usuarioId;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearIntercambio(@RequestBody CrearIntercambioRequest request) {
        try {
            Intercambio intercambio = intercambioUseCase.crearIntercambio(
                    request.getUsuarioOfreceId(),
                    request.getLibroOfrecidoId(),
                    request.getLibroSolicitadoId()
            );
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
                                                @RequestBody AceptarRechazarIntercambioRequest request) {
        try {
            Intercambio intercambio = intercambioUseCase.aceptarIntercambio(intercambioId, request.getUsuarioId());
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
                                                 @RequestBody AceptarRechazarIntercambioRequest request) {
        try {
            Intercambio intercambio = intercambioUseCase.rechazarIntercambio(intercambioId, request.getUsuarioId());
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

    @GetMapping("/{intercambioId}")
    public ResponseEntity<?> consultarIntercambio(@PathVariable Long intercambioId) {
        try {
            Intercambio intercambio = intercambioUseCase.consultarIntercambio(intercambioId);
            return ResponseEntity.ok(intercambio);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar intercambio: " + e.getMessage());
        }
    }
}