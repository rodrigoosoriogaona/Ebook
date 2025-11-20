package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacionIntercambioJpaRepository extends JpaRepository<PublicacionIntercambioData, Long> {
    List<PublicacionIntercambioData> findByEstado(String estado);
    List<PublicacionIntercambioData> findByUsuarioPropietarioId(Long usuarioId);
}