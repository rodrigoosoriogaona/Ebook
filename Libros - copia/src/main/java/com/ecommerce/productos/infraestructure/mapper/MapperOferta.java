package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.OfertaIntercambio;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.OfertaIntercambioData;
import org.springframework.stereotype.Component;

@Component
public class MapperOferta {

    public OfertaIntercambioData toData(OfertaIntercambio oferta) {
        OfertaIntercambioData ofertaData = new OfertaIntercambioData();
        ofertaData.setIdOferta(oferta.getIdOferta());
        ofertaData.setIntercambioId(oferta.getIntercambioId());
        ofertaData.setMensaje(oferta.getMensaje());
        ofertaData.setAceptada(oferta.getAceptada());
        return ofertaData;
    }

    public OfertaIntercambio toDomain(OfertaIntercambioData ofertaData) {
        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setIdOferta(ofertaData.getIdOferta());
        oferta.setIntercambioId(ofertaData.getIntercambioId());
        oferta.setMensaje(ofertaData.getMensaje());
        oferta.setAceptada(ofertaData.getAceptada());
        return oferta;
    }
}