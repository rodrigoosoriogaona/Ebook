package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra;

import com.ecommerce.productos.domain.model.Compra;
import com.ecommerce.productos.domain.model.gateway.CompraGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra.CompraData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra.CompraDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperCompra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompraDataGatewayImpl implements CompraGateway {

    private final CompraDataJpaRepository repository;
    private final MapperCompra mapper;

    @Override
    public Compra guardar(Compra compra) {
        CompraData entity = mapper.toData(compra);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Compra> buscarPorTransaccionId(Long transaccionId) {
        return repository.findByTransaccionId(transaccionId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Compra> buscarPorId(Long idCompra) {
        return repository.findById(idCompra)
                .map(mapper::toDomain);
    }
}