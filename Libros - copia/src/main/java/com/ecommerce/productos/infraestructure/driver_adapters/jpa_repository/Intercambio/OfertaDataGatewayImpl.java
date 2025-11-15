package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.domain.model.gateway.OfertaGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.IntercambioData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.OfertaIntercambioDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperOferta;
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
    public List<OfertaIntercambio> buscarPorIntercambioId(Long intercambioId) {
        return repository.findByIntercambioId(intercambioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}