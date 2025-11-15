package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion;

import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.domain.model.gateway.TransaccionGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion.TransaccionData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion.TransaccionDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperTransaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TransaccionDataGatewayImpl implements TransaccionGateway {

    private final TransaccionDataJpaRepository repository;
    private final MapperTransaccion mapper;

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        TransaccionData entity = mapper.toData(transaccion);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Transaccion> buscarPorId(Long idTransaccion) {
        return repository.findById(idTransaccion)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaccion> buscarPorCompradorId(Long compradorId) {
        return repository.findByCompradorId(compradorId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaccion> buscarPorVendedorId(Long vendedorId) {
        return repository.findByVendedorId(vendedorId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeTransaccion(Long idTransaccion) {
        return repository.existsById(idTransaccion);
    }
}