package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.domain.model.Intercambio;
import com.ecommerce.productos.domain.model.gateway.IntercambioGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.IntercambioData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.IntercambioDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperIntercambio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class IntercambioDataGatewayImpl implements IntercambioGateway {

    private final IntercambioDataJpaRepository repository;
    private final MapperIntercambio mapper;

    @Override
    public Intercambio guardar(Intercambio intercambio) {
        IntercambioData entity = mapper.toData(intercambio);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Intercambio> buscarPorId(Long idIntercambio) {
        return repository.findById(idIntercambio)
                .map(mapper::toDomain);
    }

    @Override
    public List<Intercambio> buscarPorUsuarioOfreceId(Long usuarioId) {
        return repository.findByUsuarioOfreceId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Intercambio> buscarPorUsuarioSolicitaId(Long usuarioId) {
        return repository.findByUsuarioSolicitaId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeIntercambio(Long idIntercambio) {
        return repository.existsById(idIntercambio);
    }
}