package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Oferta;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfertaIntercambioDataJpaRepository extends JpaRepository<OfertaIntercambioData, Long> {
    List<OfertaIntercambioData> findByPublicacionId(Long publicacionId);
    List<OfertaIntercambioData> findByUsuarioOfertanteId(Long usuarioOfertanteId);
}