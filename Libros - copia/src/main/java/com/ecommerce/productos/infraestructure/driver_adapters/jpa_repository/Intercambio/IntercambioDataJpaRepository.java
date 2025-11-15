package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.IntercambioData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IntercambioDataJpaRepository extends JpaRepository<IntercambioData, Long> {
    List<IntercambioData> findByUsuarioOfreceId(Long usuarioId);
    List<IntercambioData> findByUsuarioSolicitaId(Long usuarioId);
    Optional<IntercambioData> findByLibroOfrecidoId(Long libroId);
    Optional<IntercambioData> findByLibroSolicitadoId(Long libroId);
}