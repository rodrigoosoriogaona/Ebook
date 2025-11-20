package com.ecommerce.libros.Infraestructure.mapper;

import com.ecommerce.libros.domain.model.OfertaIntercambio;
import com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Oferta.OfertaIntercambioData;
import org.springframework.stereotype.Component;

@Component
public class MapperOferta {

    public OfertaIntercambioData toData(OfertaIntercambio domain) {
        if (domain == null) return null;

        OfertaIntercambioData data = new OfertaIntercambioData();
        data.setIdOferta(domain.getIdOferta());
        data.setPublicacionId(domain.getPublicacionId());
        data.setUsuarioOfertanteId(domain.getUsuarioOfertanteId());
        data.setLibroOfertadoId(domain.getLibroOfertadoId());
        data.setEstado(domain.getEstado());
        data.setMensaje(domain.getMensaje());
        data.setFechaCreacion(domain.getFechaCreacion());
        data.setFechaActualizacion(domain.getFechaActualizacion());
        return data;
    }

    public OfertaIntercambio toDomain(OfertaIntercambioData data) {
        if (data == null) return null;

        OfertaIntercambio domain = new OfertaIntercambio();
        domain.setIdOferta(data.getIdOferta());
        domain.setPublicacionId(data.getPublicacionId());
        domain.setUsuarioOfertanteId(data.getUsuarioOfertanteId());
        domain.setLibroOfertadoId(data.getLibroOfertadoId());
        domain.setEstado(data.getEstado());
        domain.setMensaje(data.getMensaje());
        domain.setFechaCreacion(data.getFechaCreacion());
        domain.setFechaActualizacion(data.getFechaActualizacion());
        return domain;
    }
}