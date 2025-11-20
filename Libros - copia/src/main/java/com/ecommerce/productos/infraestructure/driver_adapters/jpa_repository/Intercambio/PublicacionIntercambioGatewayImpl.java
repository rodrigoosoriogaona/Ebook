package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.domain.model.PublicacionIntercambio;
import com.ecommerce.productos.domain.model.gateway.PublicacionIntercambioGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperPublicacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PublicacionIntercambioGatewayImpl implements PublicacionIntercambioGateway {

    private final PublicacionIntercambioJpaRepository repository;
    private final MapperPublicacion mapper;

    @Override
    public PublicacionIntercambio guardar(PublicacionIntercambio publicacion) {
        PublicacionIntercambioData entity = mapper.toData(publicacion);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<PublicacionIntercambio> buscarPorId(Long idPublicacion) {
        return repository.findById(idPublicacion)
                .map(mapper::toDomain);
    }

    @Override
    public List<PublicacionIntercambio> buscarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PublicacionIntercambio> buscarActivas() {
        return repository.findByEstado("ACTIVA").stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PublicacionIntercambio> buscarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioPropietarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePublicacion(Long idPublicacion) {
        return repository.existsById(idPublicacion);
    }
}