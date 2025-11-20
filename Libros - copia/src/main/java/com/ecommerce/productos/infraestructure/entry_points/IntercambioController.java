package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.domain.model.PublicacionIntercambio;
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

    // ==================== PUBLICACIONES ====================

    /**
     * Crea una publicación de intercambio (libro ofrecido visible para todos)
     * POST /api/ecommerce/intercambios/publicaciones/crear
     */
    @PostMapping("/publicaciones/crear")
    public ResponseEntity<?> crearPublicacion(@RequestBody CrearPublicacionRequest request) {
        try {
            PublicacionIntercambio publicacion = intercambioUseCase.crearPublicacion(
                    request.getUsuarioPropietarioId(),
                    request.getLibroOfrecidoId(),
                    request.getDescripcion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacion);
        } catch (UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                 LibroNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear publicación: " + e.getMessage());
        }
    }

    /**
     * Lista todas las publicaciones activas (visible para todos)
     * GET /api/ecommerce/intercambios/publicaciones/activas
     */
    @GetMapping("/publicaciones/activas")
    public ResponseEntity<?> listarPublicacionesActivas() {
        try {
            List<PublicacionIntercambio> publicaciones = intercambioUseCase.listarPublicacionesActivas();
            return ResponseEntity.ok(publicaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar publicaciones: " + e.getMessage());
        }
    }

    /**
     * Lista mis publicaciones (del usuario autenticado)
     * GET /api/ecommerce/intercambios/publicaciones/mis-publicaciones/{usuarioId}
     */
    @GetMapping("/publicaciones/mis-publicaciones/{usuarioId}")
    public ResponseEntity<?> listarMisPublicaciones(@PathVariable Long usuarioId) {
        try {
            List<PublicacionIntercambio> publicaciones = intercambioUseCase.listarMisPublicaciones(usuarioId);
            return ResponseEntity.ok(publicaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar mis publicaciones: " + e.getMessage());
        }
    }

    /**
     * Consulta una publicación específica
     * GET /api/ecommerce/intercambios/publicaciones/{publicacionId}
     */
    @GetMapping("/publicaciones/{publicacionId}")
    public ResponseEntity<?> consultarPublicacion(@PathVariable Long publicacionId) {
        try {
            PublicacionIntercambio publicacion = intercambioUseCase.consultarPublicacion(publicacionId);
            return ResponseEntity.ok(publicacion);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar publicación: " + e.getMessage());
        }
    }

    // ==================== OFERTAS ====================

    /**
     * Crea una oferta sobre una publicación activa
     * POST /api/ecommerce/intercambios/ofertas/crear
     */
    @PostMapping("/ofertas/crear")
    public ResponseEntity<?> crearOferta(@RequestBody CrearOfertaRequest request) {
        try {
            OfertaIntercambio oferta = intercambioUseCase.crearOferta(
                    request.getPublicacionId(),
                    request.getUsuarioOferenteId(),
                    request.getLibroOfrecidoId(),
                    request.getMensaje()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(oferta);
        } catch (IntercambioNoEncontradoException | IntercambioNoValidoException |
                 UsuarioNoAutorizadoException | LibroNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear oferta: " + e.getMessage());
        }
    }

    /**
     * Aceptar una oferta (solo el dueño de la publicación)
     * PUT /api/ecommerce/intercambios/ofertas/{ofertaId}/aceptar
     */
    @PutMapping("/ofertas/{ofertaId}/aceptar")
    public ResponseEntity<?> aceptarOferta(@PathVariable Long ofertaId,
                                           @RequestBody AccionOfertaRequest request) {
        try {
            OfertaIntercambio oferta = intercambioUseCase.aceptarOferta(ofertaId, request.getUsuarioPropietarioId());
            return ResponseEntity.ok(oferta);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException | IntercambioNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aceptar oferta: " + e.getMessage());
        }
    }

    /**
     * Rechazar una oferta (solo el dueño de la publicación)
     * PUT /api/ecommerce/intercambios/ofertas/{ofertaId}/rechazar
     */
    @PutMapping("/ofertas/{ofertaId}/rechazar")
    public ResponseEntity<?> rechazarOferta(@PathVariable Long ofertaId,
                                            @RequestBody AccionOfertaRequest request) {
        try {
            OfertaIntercambio oferta = intercambioUseCase.rechazarOferta(ofertaId, request.getUsuarioPropietarioId());
            return ResponseEntity.ok(oferta);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNoAutorizadoException | IntercambioNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al rechazar oferta: " + e.getMessage());
        }
    }

    /**
     * Lista todas las ofertas recibidas para una publicación
     * GET /api/ecommerce/intercambios/publicaciones/{publicacionId}/ofertas
     */
    @GetMapping("/publicaciones/{publicacionId}/ofertas")
    public ResponseEntity<?> listarOfertasDePublicacion(@PathVariable Long publicacionId) {
        try {
            List<OfertaIntercambio> ofertas = intercambioUseCase.listarOfertasDePublicacion(publicacionId);
            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar ofertas: " + e.getMessage());
        }
    }

    /**
     * Lista mis ofertas realizadas (como oferente)
     * GET /api/ecommerce/intercambios/ofertas/mis-ofertas/{usuarioId}
     */
    @GetMapping("/ofertas/mis-ofertas/{usuarioId}")
    public ResponseEntity<?> listarMisOfertas(@PathVariable Long usuarioId) {
        try {
            List<OfertaIntercambio> ofertas = intercambioUseCase.listarMisOfertas(usuarioId);
            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar mis ofertas: " + e.getMessage());
        }
    }

    /**
     * Consulta una oferta específica
     * GET /api/ecommerce/intercambios/ofertas/{ofertaId}
     */
    @GetMapping("/ofertas/{ofertaId}")
    public ResponseEntity<?> consultarOferta(@PathVariable Long ofertaId) {
        try {
            OfertaIntercambio oferta = intercambioUseCase.consultarOferta(ofertaId);
            return ResponseEntity.ok(oferta);
        } catch (IntercambioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar oferta: " + e.getMessage());
        }
    }

    // ==================== DTOs ====================

    @Data
    public static class CrearPublicacionRequest {
        private Long usuarioPropietarioId;
        private Long libroOfrecidoId;
        private String descripcion;
    }

    @Data
    public static class CrearOfertaRequest {
        private Long publicacionId;
        private Long usuarioOferenteId;
        private Long libroOfrecidoId;
        private String mensaje;
    }

    @Data
    public static class AccionOfertaRequest {
        private Long usuarioPropietarioId;
    }
}