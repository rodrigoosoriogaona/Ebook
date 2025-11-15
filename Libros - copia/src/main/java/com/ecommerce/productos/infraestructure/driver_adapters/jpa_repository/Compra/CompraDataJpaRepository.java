package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra.CompraData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompraDataJpaRepository extends JpaRepository<CompraData, Long> {
    Optional<CompraData> findByTransaccionId(Long transaccionId);
}