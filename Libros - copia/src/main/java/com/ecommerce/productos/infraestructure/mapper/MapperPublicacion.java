package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.PublicacionIntercambio;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.PublicacionIntercambioData;
import org.springframework.stereotype.Component;

@Component
public class MapperPublicacion {

    public PublicacionIntercambioData toData(PublicacionIntercambio publicacion) {
        PublicacionIntercambioData data = new PublicacionIntercambioData();
        data.setIdPublicacion(publicacion.getIdPublicacion());
        data.setLibroOfrecidoId(publicacion.getLibroOfrecidoId());
        data.setUsuarioPropietarioId(publicacion.getUsuarioPropietarioId());
        data.setEstado(publicacion.getEstado());
        data.setDescripcion(publicacion.getDescripcion());
        data.setFechaCreacion(publicacion.getFechaCreacion());
        data.setFechaActualizacion(publicacion.getFechaActualizacion());
        return data;
    }

    public PublicacionIntercambio toDomain(PublicacionIntercambioData data) {
        PublicacionIntercambio publicacion = new PublicacionIntercambio();
        publicacion.setIdPublicacion(data.getIdPublicacion());
        publicacion.setLibroOfrecidoId(data.getLibroOfrecidoId());
        publicacion.setUsuarioPropietarioId(data.getUsuarioPropietarioId());
        publicacion.setEstado(data.getEstado());
        publicacion.setDescripcion(data.getDescripcion());
        publicacion.setFechaCreacion(data.getFechaCreacion());
        publicacion.setFechaActualizacion(data.getFechaActualizacion());
        return publicacion;
    }
}