package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.OfertaIntercambioData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OfertaIntercambioDataJpaRepository extends JpaRepository<OfertaIntercambioData, Long> {
    List<OfertaIntercambioData> findByIntercambioId(Long intercambioId);
    List<OfertaIntercambioData> findByAceptada(Boolean aceptada);
}