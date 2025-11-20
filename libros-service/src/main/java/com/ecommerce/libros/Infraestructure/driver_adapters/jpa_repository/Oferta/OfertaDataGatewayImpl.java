package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Oferta;

import com.ecommerce.libros.domain.model.OfertaIntercambio;
import com.ecommerce.libros.domain.model.Gateway.OfertaGateway;
import com.ecommerce.libros.Infraestructure.mapper.MapperOferta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OfertaDataGatewayImpl implements OfertaGateway {

    private final OfertaIntercambioDataJpaRepository repository;
    private final MapperOferta mapper;

    @Override
    public OfertaIntercambio guardar(OfertaIntercambio oferta) {
        OfertaIntercambioData entity = mapper.toData(oferta);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<OfertaIntercambio> buscarPorId(Long idOferta) {
        return repository.findById(idOferta)
                .map(mapper::toDomain);
    }

    @Override
    public List<OfertaIntercambio> buscarPorPublicacionId(Long publicacionId) {
        return repository.findByPublicacionId(publicacionId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaIntercambio> buscarPorUsuarioOfertanteId(Long usuarioId) {
        return repository.findByUsuarioOfertanteId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    // ELIMINADO: buscarPorPublicacionYEstado - No está en el repository
}