package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.domain.model.gateway.OfertaGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperOferta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OfertaDataGatewayImpl implements OfertaGateway {

    private final OfertaIntercambioDataJpaRepository ofertaIntercambioDataJpaRepository;
    private final MapperOferta mapperOferta;

    @Override
    public OfertaIntercambio guardar(OfertaIntercambio oferta) {
        OfertaIntercambioData ofertaData = mapperOferta.toData(oferta);
        OfertaIntercambioData savedData = ofertaIntercambioDataJpaRepository.save(ofertaData);
        return mapperOferta.toDomain(savedData);
    }

    @Override
    public Optional<OfertaIntercambio> buscarPorId(Long idOferta) {
        return ofertaIntercambioDataJpaRepository.findByIdOferta(idOferta)
                .map(mapperOferta::toDomain);
    }

    @Override
    public List<OfertaIntercambio> buscarPorPublicacionId(Long publicacionId) {
        return ofertaIntercambioDataJpaRepository.findByPublicacionId(publicacionId)
                .stream()
                .map(mapperOferta::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaIntercambio> buscarPorUsuarioOferente(Long usuarioOferenteId) {
        return ofertaIntercambioDataJpaRepository.findByUsuarioOferenteId(usuarioOferenteId)
                .stream()
                .map(mapperOferta::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaIntercambio> buscarPorEstado(String estado) {
        return ofertaIntercambioDataJpaRepository.findByEstado(estado)
                .stream()
                .map(mapperOferta::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizarEstadoMasivo(List<Long> idsOfertas, String estado) {
        ofertaIntercambioDataJpaRepository.updateEstadoByIds(idsOfertas, estado);
    }

    // Si necesitas métodos específicos para ofertas aceptadas/pendientes:
    public List<OfertaIntercambio> buscarOfertasPendientesPorPublicacion(Long publicacionId) {
        return ofertaIntercambioDataJpaRepository.findOfertasPendientesByPublicacion(publicacionId)
                .stream()
                .map(mapperOferta::toDomain)
                .collect(Collectors.toList());
    }

    public List<OfertaIntercambio> buscarOfertasAceptadasPorPublicacion(Long publicacionId) {
        return ofertaIntercambioDataJpaRepository.findOfertasAceptadasByPublicacion(publicacionId)
                .stream()
                .map(mapperOferta::toDomain)
                .collect(Collectors.toList());
    }
}