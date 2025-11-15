package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.domain.model.gateway.VentaGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta.VentaData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta.VentaDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VentaDataGatewayImpl implements VentaGateway {

    private final VentaDataJpaRepository repository;
    private final MapperVenta mapper;

    @Override
    public Venta guardar(Venta venta) {
        VentaData entity = mapper.toData(venta);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Venta> buscarPorTransaccionId(Long transaccionId) {
        return repository.findByTransaccionId(transaccionId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Venta> buscarPorId(Long idVenta) {
        return repository.findById(idVenta)
                .map(mapper::toDomain);
    }
}