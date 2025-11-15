package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta.VentaData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VentaDataJpaRepository extends JpaRepository<VentaData, Long> {
    Optional<VentaData> findByTransaccionId(Long transaccionId);
}