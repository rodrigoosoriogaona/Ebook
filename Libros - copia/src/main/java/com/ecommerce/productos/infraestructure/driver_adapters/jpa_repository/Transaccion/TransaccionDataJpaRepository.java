package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion.TransaccionData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransaccionDataJpaRepository extends JpaRepository<TransaccionData, Long> {
    List<TransaccionData> findByCompradorId(Long compradorId);
    List<TransaccionData> findByVendedorId(Long vendedorId);
    Optional<TransaccionData> findByLibroId(Long libroId);
}