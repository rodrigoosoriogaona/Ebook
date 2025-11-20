package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Transaccion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransaccionDataJpaRepository extends JpaRepository<TransaccionData, Long> {
    List<TransaccionData> findByUsuarioCompradorId(Long usuarioCompradorId);
    List<TransaccionData> findByPublicacionId(Long publicacionId);
}