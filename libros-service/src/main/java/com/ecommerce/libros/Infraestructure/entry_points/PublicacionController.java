package com.ecommerce.libros.Infraestructure.entry_points;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.domain.UseCase.PublicacionUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PublicacionController {

    private final PublicacionUseCase publicacionUseCase;

    @Data
    public static class CrearPublicacionVentaRequest {
        private Long libroId;
        private Double precio;
    }

    @Data
    public static class CrearPublicacionIntercambioRequest {
        private Long libroId;
        private String condiciones;
    }

    @PostMapping("/venta/crear/{usuarioId}")
    public ResponseEntity<?> crearPublicacionVenta(@PathVariable Long usuarioId,
                                                   @RequestBody CrearPublicacionVentaRequest request) {
        try {
            Publicacion publicacion = publicacionUseCase.crearPublicacionVenta(usuarioId, request.getLibroId(), request.getPrecio());
            return ResponseEntity.ok(publicacion);
        } catch (UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                 LibroNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear publicación de venta: " + e.getMessage());
        }
    }

    @PostMapping("/intercambio/crear/{usuarioId}")
    public ResponseEntity<?> crearPublicacionIntercambio(@PathVariable Long usuarioId,
                                                         @RequestBody CrearPublicacionIntercambioRequest request) {
        try {
            Publicacion publicacion = publicacionUseCase.crearPublicacionIntercambio(usuarioId, request.getLibroId(), request.getCondiciones());
            return ResponseEntity.ok(publicacion);
        } catch (UsuarioNoEncontradoException | UsuarioNoAutorizadoException |
                 LibroNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear publicación de intercambio: " + e.getMessage());
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<?> consultarPublicacionesActivas() {
        try {
            List<Publicacion> publicaciones = publicacionUseCase.consultarPublicacionesActivas();
            return ResponseEntity.ok(publicaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar publicaciones: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> consultarPublicacionesPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Publicacion> publicaciones = publicacionUseCase.consultarPublicacionesPorUsuario(usuarioId);
            return ResponseEntity.ok(publicaciones);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar publicaciones: " + e.getMessage());
        }
    }

    @PutMapping("/{publicacionId}/cerrar/{usuarioId}")
    public ResponseEntity<?> cerrarPublicacion(@PathVariable Long publicacionId, @PathVariable Long usuarioId) {
        try {
            Publicacion publicacion = publicacionUseCase.cerrarPublicacion(publicacionId, usuarioId);
            return ResponseEntity.ok(publicacion);
        } catch (PublicacionNoEncontradaException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cerrar publicación: " + e.getMessage());
        }
    }
}