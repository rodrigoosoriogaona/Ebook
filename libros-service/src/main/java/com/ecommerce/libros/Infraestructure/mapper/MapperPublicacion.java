package com.ecommerce.libros.Infraestructure.mapper;

import com.ecommerce.libros.domain.model.Publicacion;
import com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Publicacion.PublicacionData;
import org.springframework.stereotype.Component;

@Component
public class MapperPublicacion {

    public PublicacionData toData(Publicacion domain) {
        if (domain == null) return null;

        PublicacionData data = new PublicacionData();
        data.setIdPublicacion(domain.getIdPublicacion());
        data.setUsuarioId(domain.getUsuarioId());
        data.setLibroId(domain.getLibroId());
        data.setTipo(domain.getTipo());
        data.setEstado(domain.getEstado());
        data.setPrecioVenta(domain.getPrecioVenta());
        data.setCondicionesIntercambio(domain.getCondicionesIntercambio());
        data.setFechaCreacion(domain.getFechaCreacion());
        data.setFechaActualizacion(domain.getFechaActualizacion());
        return data;
    }

    public Publicacion toDomain(PublicacionData data) {
        if (data == null) return null;

        Publicacion domain = new Publicacion();
        domain.setIdPublicacion(data.getIdPublicacion());
        domain.setUsuarioId(data.getUsuarioId());
        domain.setLibroId(data.getLibroId());
        domain.setTipo(data.getTipo());
        domain.setEstado(data.getEstado());
        domain.setPrecioVenta(data.getPrecioVenta());
        domain.setCondicionesIntercambio(data.getCondicionesIntercambio());
        domain.setFechaCreacion(data.getFechaCreacion());
        domain.setFechaActualizacion(data.getFechaActualizacion());
        return domain;
    }
}