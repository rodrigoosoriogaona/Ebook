package com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository;

import com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository.PagoData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagoDataJpaRepository extends JpaRepository<PagoData, Long> {
    Optional<PagoData> findByTransaccionId(Long transaccionId);
    Optional<PagoData> findByReferencia(String referencia);
}