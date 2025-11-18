package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.LibroNoEncontradoException;
import com.ecommerce.productos.domain.exception.UsuarioNoAutorizadoException;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.usecase.LibroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LibroController {

    private final LibroUseCase libroUseCase;

    @PostMapping("/crear")
    public ResponseEntity<?> crearLibro(@RequestBody Libro libro) {
        try {
            Libro nuevoLibro = libroUseCase.crearLibro(libro);
            return ResponseEntity.ok(nuevoLibro);
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear libro: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{idLibro}")
    public ResponseEntity<?> actualizarLibro(@PathVariable Long idLibro, @RequestBody Libro libro) {
        try {
            Libro libroActualizado = libroUseCase.actualizarLibro(idLibro, libro);
            return ResponseEntity.ok(libroActualizado);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar libro: " + e.getMessage());
        }
    }

    @GetMapping("/{idLibro}")
    public ResponseEntity<?> consultarLibro(@PathVariable Long idLibro) {
        try {
            Libro libro = libroUseCase.consultarLibro(idLibro);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar libro: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarLibrosPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Libro> libros = libroUseCase.buscarLibrosPorUsuario(usuarioId);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<?> buscarLibrosPorTitulo(@RequestParam String titulo) {
        try {
            List<Libro> libros = libroUseCase.buscarLibrosPorTitulo(titulo);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<?> buscarLibrosPorAutor(@RequestParam String autor) {
        try {
            List<Libro> libros = libroUseCase.buscarLibrosPorAutor(autor);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idLibro}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long idLibro, @RequestParam Long usuarioId) {
        try {
            libroUseCase.eliminarLibro(idLibro, usuarioId);
            return ResponseEntity.ok("Libro eliminado correctamente");
        } catch (LibroNoEncontradoException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar libro: " + e.getMessage());
        }
    }
}
