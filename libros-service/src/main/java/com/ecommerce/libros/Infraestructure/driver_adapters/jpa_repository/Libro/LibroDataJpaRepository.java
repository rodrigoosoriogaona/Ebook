package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroDataJpaRepository extends JpaRepository<LibroData, Long> {
    List<LibroData> findByUsuarioId(Long usuarioId);
    List<LibroData> findByDisponible(Boolean disponible);
}