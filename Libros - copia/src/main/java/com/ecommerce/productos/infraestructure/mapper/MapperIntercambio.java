package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Intercambio;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio.IntercambioData;
import org.springframework.stereotype.Component;

@Component
public class MapperIntercambio {

    public IntercambioData toData(Intercambio intercambio) {
        IntercambioData intercambioData = new IntercambioData();
        intercambioData.setIdIntercambio(intercambio.getIdIntercambio());
        intercambioData.setLibroOfrecidoId(intercambio.getLibroOfrecidoId());
        intercambioData.setLibroSolicitadoId(intercambio.getLibroSolicitadoId());
        intercambioData.setUsuarioOfreceId(intercambio.getUsuarioOfreceId());
        intercambioData.setUsuarioSolicitaId(intercambio.getUsuarioSolicitaId());
        intercambioData.setEstado(intercambio.getEstado());
        intercambioData.setFechaCreacion(intercambio.getFechaCreacion());
        intercambioData.setFechaActualizacion(intercambio.getFechaActualizacion());
        return intercambioData;
    }

    public Intercambio toDomain(IntercambioData intercambioData) {
        Intercambio intercambio = new Intercambio();
        intercambio.setIdIntercambio(intercambioData.getIdIntercambio());
        intercambio.setLibroOfrecidoId(intercambioData.getLibroOfrecidoId());
        intercambio.setLibroSolicitadoId(intercambioData.getLibroSolicitadoId());
        intercambio.setUsuarioOfreceId(intercambioData.getUsuarioOfreceId());
        intercambio.setUsuarioSolicitaId(intercambioData.getUsuarioSolicitaId());
        intercambio.setEstado(intercambioData.getEstado());
        intercambio.setFechaCreacion(intercambioData.getFechaCreacion());
        intercambio.setFechaActualizacion(intercambioData.getFechaActualizacion());
        return intercambio;
    }
}