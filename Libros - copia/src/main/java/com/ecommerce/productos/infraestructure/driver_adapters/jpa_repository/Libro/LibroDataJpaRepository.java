package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro.LibroData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroDataJpaRepository extends JpaRepository<LibroData, Long> {
    List<LibroData> findByUsuarioId(Long usuarioId);
    List<LibroData> findByTituloContainingIgnoreCase(String titulo);
    List<LibroData> findByAutorContainingIgnoreCase(String autor);
    Optional<LibroData> findByIsbn(String isbn);
}