package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.OfertaIntercambioData;
import org.springframework.stereotype.Component;

@Component
public class MapperOferta {

    public OfertaIntercambioData toData(OfertaIntercambio oferta) {
        OfertaIntercambioData ofertaData = new OfertaIntercambioData();
        ofertaData.setIdOferta(oferta.getIdOferta());
        ofertaData.setPublicacionId(oferta.getPublicacionId());
        ofertaData.setUsuarioOferenteId(oferta.getUsuarioOferenteId());
        ofertaData.setLibroOfrecidoId(oferta.getLibroOfrecidoId());
        ofertaData.setMensaje(oferta.getMensaje());
        ofertaData.setEstado(oferta.getEstado());
        ofertaData.setFechaCreacion(oferta.getFechaCreacion());
        ofertaData.setFechaActualizacion(oferta.getFechaActualizacion());
        return ofertaData;
    }

    public OfertaIntercambio toDomain(OfertaIntercambioData ofertaData) {
        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setIdOferta(ofertaData.getIdOferta());
        oferta.setPublicacionId(ofertaData.getPublicacionId());
        oferta.setUsuarioOferenteId(ofertaData.getUsuarioOferenteId());
        oferta.setLibroOfrecidoId(ofertaData.getLibroOfrecidoId());
        oferta.setMensaje(ofertaData.getMensaje());
        oferta.setEstado(ofertaData.getEstado());
        oferta.setFechaCreacion(ofertaData.getFechaCreacion());
        oferta.setFechaActualizacion(ofertaData.getFechaActualizacion());
        return oferta;
    }
}