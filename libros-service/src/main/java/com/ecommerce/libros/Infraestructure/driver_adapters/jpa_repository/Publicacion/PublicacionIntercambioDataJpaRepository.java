package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Publicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacionIntercambioDataJpaRepository extends JpaRepository<PublicacionData, Long> {
    List<PublicacionData> findByEstado(String estado);
    List<PublicacionData> findByUsuarioId(Long usuarioId);
}