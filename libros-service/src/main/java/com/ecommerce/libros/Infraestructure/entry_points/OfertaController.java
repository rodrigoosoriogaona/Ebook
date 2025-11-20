    package com.ecommerce.libros.Infraestructure.entry_points;

    import com.ecommerce.libros.domain.exception.*;
    import com.ecommerce.libros.domain.model.OfertaIntercambio;
    import com.ecommerce.libros.domain.UseCase.OfertaUseCase;
    import lombok.Data;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;

    @RestController
    @RequestMapping("/api/ofertas")
    @RequiredArgsConstructor
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public class OfertaController {

        private final OfertaUseCase ofertaUseCase;

        @Data
        public static class CrearOfertaRequest {
            private Long publicacionId;
            private Long libroOfertadoId;
            private String mensaje;
        }

        @Data
        public static class AceptarRechazarOfertaRequest {
            private Long usuarioId;
        }

        @PostMapping("/crear/{usuarioOfertanteId}")
        public ResponseEntity<?> crearOferta(@PathVariable Long usuarioOfertanteId,
                                             @RequestBody CrearOfertaRequest request) {
            try {
                OfertaIntercambio oferta = ofertaUseCase.crearOfertaIntercambio(
                        request.getPublicacionId(),
                        usuarioOfertanteId,
                        request.getLibroOfertadoId(),
                        request.getMensaje()
                );
                return ResponseEntity.ok(oferta);
            } catch (PublicacionNoEncontradaException | PublicacionNoActivaException |
                     UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                     LibroNoDisponibleException | TransaccionNoValidaException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al crear oferta: " + e.getMessage());
            }
        }

        @PutMapping("/{ofertaId}/aceptar")
        public ResponseEntity<?> aceptarOferta(@PathVariable Long ofertaId,
                                               @RequestBody AceptarRechazarOfertaRequest request) {
            try {
                OfertaIntercambio oferta = ofertaUseCase.aceptarOferta(ofertaId, request.getUsuarioId());
                return ResponseEntity.ok(oferta);
            } catch (OfertaNoEncontradaException | PublicacionNoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (UsuarioNoAutorizadoException | TransaccionNoValidaException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al aceptar oferta: " + e.getMessage());
            }
        }

        @PutMapping("/{ofertaId}/rechazar")
        public ResponseEntity<?> rechazarOferta(@PathVariable Long ofertaId,
                                                @RequestBody AceptarRechazarOfertaRequest request) {
            try {
                OfertaIntercambio oferta = ofertaUseCase.rechazarOferta(ofertaId, request.getUsuarioId());
                return ResponseEntity.ok(oferta);
            } catch (OfertaNoEncontradaException | PublicacionNoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (UsuarioNoAutorizadoException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al rechazar oferta: " + e.getMessage());
            }
        }

        @GetMapping("/publicacion/{publicacionId}")
        public ResponseEntity<?> consultarOfertasPorPublicacion(@PathVariable Long publicacionId) {
            try {
                List<OfertaIntercambio> ofertas = ofertaUseCase.consultarOfertasPorPublicacion(publicacionId);
                return ResponseEntity.ok(ofertas);
            } catch (PublicacionNoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al consultar ofertas: " + e.getMessage());
            }
        }

        @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<?> consultarOfertasPorUsuario(@PathVariable Long usuarioId) {
            try {
                List<OfertaIntercambio> ofertas = ofertaUseCase.consultarOfertasPorUsuario(usuarioId);
                return ResponseEntity.ok(ofertas);
            } catch (UsuarioNoEncontradoException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al consultar ofertas: " + e.getMessage());
            }
        }
    }