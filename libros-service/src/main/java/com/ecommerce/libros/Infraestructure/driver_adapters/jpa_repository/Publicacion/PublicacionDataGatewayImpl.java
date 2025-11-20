package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Publicacion;

import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.domain.model.Gateway.PublicacionGateway;
import com.ecommerce.libros.Infraestructure.mapper.MapperPublicacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PublicacionDataGatewayImpl implements PublicacionGateway {

    private final PublicacionIntercambioDataJpaRepository repository;
    private final MapperPublicacion mapper;

    @Override
    public Publicacion guardar(Publicacion publicacion) {
        PublicacionData entity = mapper.toData(publicacion);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Publicacion> buscarPorId(Long idPublicacion) {
        return repository.findById(idPublicacion)
                .map(mapper::toDomain);
    }

    @Override
    public List<Publicacion> buscarPorEstado(String estado) {
        return repository.findByEstado(estado).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Publicacion> buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    // ELIMINADO: buscarPorTipoYEstado - No está en el repository
}