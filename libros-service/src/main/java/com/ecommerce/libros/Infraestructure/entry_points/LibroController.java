package com.ecommerce.libros.Infraestructure.entry_points;

import com.ecommerce.libros.domain.exception.*;
import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.UseCase.LibroUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LibroController {

    private final LibroUseCase libroUseCase;

    @Data
    public static class CrearLibroRequest {
        private String titulo;
        private String autor;
        private String genero;
        private String sinopsis;
        private String estadoFisico;
        private Double precio;
        private Boolean enVenta;
        private Boolean enIntercambio;
    }

    @Data
    public static class ActualizarLibroRequest {
        private String titulo;
        private String autor;
        private String genero;
        private String sinopsis;
        private String estadoFisico;
        private Double precio;
        private Boolean enVenta;
        private Boolean enIntercambio;
    }

    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<?> crearLibro(@PathVariable Long usuarioId, @RequestBody CrearLibroRequest request) {
        try {
            Libro libro = new Libro();
            libro.setTitulo(request.getTitulo());
            libro.setAutor(request.getAutor());
            libro.setGenero(request.getGenero());
            libro.setSinopsis(request.getSinopsis());
            libro.setEstadoFisico(request.getEstadoFisico());
            libro.setPrecio(request.getPrecio());
            libro.setEnVenta(request.getEnVenta());
            libro.setEnIntercambio(request.getEnIntercambio());

            Libro libroCreado = libroUseCase.crearLibro(libro, usuarioId);
            return ResponseEntity.ok(libroCreado);
        } catch (UsuarioNoEncontradoException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear libro: " + e.getMessage());
        }
    }

    @PutMapping("/{libroId}/actualizar/{usuarioId}")
    public ResponseEntity<?> actualizarLibro(@PathVariable Long libroId, @PathVariable Long usuarioId,
                                             @RequestBody ActualizarLibroRequest request) {
        try {
            Libro libroActualizado = new Libro();
            libroActualizado.setTitulo(request.getTitulo());
            libroActualizado.setAutor(request.getAutor());
            libroActualizado.setGenero(request.getGenero());
            libroActualizado.setSinopsis(request.getSinopsis());
            libroActualizado.setEstadoFisico(request.getEstadoFisico());
            libroActualizado.setPrecio(request.getPrecio());
            libroActualizado.setEnVenta(request.getEnVenta());
            libroActualizado.setEnIntercambio(request.getEnIntercambio());

            Libro libro = libroUseCase.actualizarLibro(libroId, libroActualizado, usuarioId);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar libro: " + e.getMessage());
        }
    }

    @GetMapping("/biblioteca/{usuarioId}")
    public ResponseEntity<?> consultarBiblioteca(@PathVariable Long usuarioId) {
        try {
            List<Libro> biblioteca = libroUseCase.consultarBibliotecaUsuario(usuarioId);
            return ResponseEntity.ok(biblioteca);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar biblioteca: " + e.getMessage());
        }
    }

    @GetMapping("/{libroId}")
    public ResponseEntity<?> consultarLibro(@PathVariable Long libroId) {
        try {
            Libro libro = libroUseCase.consultarLibro(libroId);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar libro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{libroId}/eliminar/{usuarioId}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long libroId, @PathVariable Long usuarioId) {
        try {
            libroUseCase.eliminarLibro(libroId, usuarioId);
            return ResponseEntity.ok("Libro eliminado correctamente");
        } catch (LibroNoEncontradoException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar libro: " + e.getMessage());
        }
    }
}