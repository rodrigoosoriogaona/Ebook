package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.LibroNoEncontradoException;
import com.ecommerce.productos.domain.exception.UsuarioNoAutorizadoException;
import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.usecase.LibroUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ecommerce/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LibroController {

    private final LibroUseCase libroUseCase;

    
    @PostMapping("/crear")
    public ResponseEntity<?> crearLibro(@RequestBody Libro libro) {
        try {
            // Al crear, el libro NO está disponible públicamente aún
            libro.setDisponible(false);
            Libro nuevoLibro = libroUseCase.crearLibro(libro);
            return ResponseEntity.ok(nuevoLibro);
        } catch (UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear libro: " + e.getMessage());
        }
    }

    // ========== PUBLICAR LIBRO (HACERLO VISIBLE) ==========
    @PutMapping("/publicar/{idLibro}")
    public ResponseEntity<?> publicarLibro(@PathVariable Long idLibro,
                                           @RequestBody PublicarLibroRequest request) {
        try {
            Libro libro = libroUseCase.consultarLibro(idLibro);

            // Verificar que el usuario sea el dueño
            if (!libro.getUsuarioId().equals(request.getUsuarioId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("No autorizado para publicar este libro");
            }

            // Marcar como disponible
            libro.setDisponible(true);
            Libro libroPublicado = libroUseCase.actualizarLibro(idLibro, libro);
            return ResponseEntity.ok(libroPublicado);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar libro: " + e.getMessage());
        }
    }

    // ========== DESPUBLICAR LIBRO ==========
    @PutMapping("/despublicar/{idLibro}")
    public ResponseEntity<?> despublicarLibro(@PathVariable Long idLibro,
                                              @RequestBody PublicarLibroRequest request) {
        try {
            Libro libro = libroUseCase.consultarLibro(idLibro);

            if (!libro.getUsuarioId().equals(request.getUsuarioId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("No autorizado para despublicar este libro");
            }

            libro.setDisponible(false);
            Libro libroActualizado = libroUseCase.actualizarLibro(idLibro, libro);
            return ResponseEntity.ok(libroActualizado);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al despublicar libro: " + e.getMessage());
        }
    }

    // ========== LISTAR TODOS LOS LIBROS PÚBLICOS (DISPONIBLES) ==========
    @GetMapping("/publicos")
    public ResponseEntity<?> listarLibrosPublicos() {
        try {
            // Este endpoint debería ser implementado en el UseCase
            // Por ahora, lo simulamos filtrando en el controlador
            List<Libro> todosLosLibros = libroUseCase.buscarLibrosPorTitulo(""); // Hack temporal
            List<Libro> librosDisponibles = todosLosLibros.stream()
                    .filter(Libro::getDisponible)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(librosDisponibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar libros: " + e.getMessage());
        }
    }

    // ========== ACTUALIZAR LIBRO ==========
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

    // ========== CONSULTAR LIBRO ESPECÍFICO ==========
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

    // ========== MIS LIBROS (DEL USUARIO) ==========
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

    // ========== BUSCAR POR TÍTULO ==========
    @GetMapping("/buscar/titulo")
    public ResponseEntity<?> buscarLibrosPorTitulo(@RequestParam String titulo) {
        try {
            List<Libro> libros = libroUseCase.buscarLibrosPorTitulo(titulo);
            // Filtrar solo los disponibles
            List<Libro> librosDisponibles = libros.stream()
                    .filter(Libro::getDisponible)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(librosDisponibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }

    // ========== BUSCAR POR AUTOR ==========
    @GetMapping("/buscar/autor")
    public ResponseEntity<?> buscarLibrosPorAutor(@RequestParam String autor) {
        try {
            List<Libro> libros = libroUseCase.buscarLibrosPorAutor(autor);
            // Filtrar solo los disponibles
            List<Libro> librosDisponibles = libros.stream()
                    .filter(Libro::getDisponible)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(librosDisponibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }

    // ========== ELIMINAR LIBRO ==========
    @DeleteMapping("/eliminar/{idLibro}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long idLibro,
                                           @RequestBody EliminarLibroRequest request) {
        try {
            libroUseCase.eliminarLibro(idLibro, request.getUsuarioId());
            return ResponseEntity.ok("Libro eliminado correctamente");
        } catch (LibroNoEncontradoException | UsuarioNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar libro: " + e.getMessage());
        }
    }

    // ========== CLASES AUXILIARES ==========
    @Data
    public static class EliminarLibroRequest {
        private Long usuarioId;
    }

    @Data
    public static class PublicarLibroRequest {
        private Long usuarioId;
    }
}