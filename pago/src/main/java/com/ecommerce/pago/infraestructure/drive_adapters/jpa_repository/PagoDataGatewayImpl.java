package com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository;

import com.ecommerce.pago.domain.model.Pago;
import com.ecommerce.pago.domain.model.Gateway.PagoGateway;
import com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository.PagoData;
import com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository.PagoDataJpaRepository;
import com.ecommerce.pago.infraestructure.mapper.MapperPago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PagoDataGatewayImpl implements PagoGateway {

    private final PagoDataJpaRepository repository;
    private final MapperPago mapper;

    @Override
    public Pago guardar(Pago pago) {
        PagoData entity = mapper.toData(pago);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Pago> buscarPorId(Long idPago) {
        return repository.findById(idPago)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Pago> buscarPorTransaccionId(Long transaccionId) {
        return repository.findByTransaccionId(transaccionId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePago(Long idPago) {
        return repository.existsById(idPago);
    }
}