package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Transaccion;

import com.ecommerce.libros.domain.model.Transaccion;
import com.ecommerce.libros.domain.model.Gateway.TransaccionGateway;
import com.ecommerce.libros.Infraestructure.mapper.MapperTransaccion;
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
    public List<Transaccion> buscarPorUsuarioCompradorId(Long usuarioId) {
        return repository.findByUsuarioCompradorId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaccion> buscarPorPublicacionId(Long publicacionId) {
        return repository.findByPublicacionId(publicacionId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}