package com.ecommerce.libros.Infraestructure.mapper;

import com.ecommerce.libros.domain.model.Transaccion;
import com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Transaccion.TransaccionData;
import org.springframework.stereotype.Component;

@Component
public class MapperTransaccion {

    public TransaccionData toData(Transaccion domain) {
        if (domain == null) return null;

        TransaccionData data = new TransaccionData();
        data.setIdTransaccion(domain.getIdTransaccion());
        data.setPublicacionId(domain.getPublicacionId());
        data.setUsuarioCompradorId(domain.getUsuarioCompradorId());
        data.setTipoPago(domain.getTipoPago());
        data.setEstado(domain.getEstado());
        data.setMonto(domain.getMonto());
        data.setMetodoPago(domain.getMetodoPago());
        data.setFechaCreacion(domain.getFechaCreacion());
        data.setFechaActualizacion(domain.getFechaActualizacion());
        return data;
    }

    public Transaccion toDomain(TransaccionData data) {
        if (data == null) return null;

        Transaccion domain = new Transaccion();
        domain.setIdTransaccion(data.getIdTransaccion());
        domain.setPublicacionId(data.getPublicacionId());
        domain.setUsuarioCompradorId(data.getUsuarioCompradorId());
        domain.setTipoPago(data.getTipoPago());
        domain.setEstado(data.getEstado());
        domain.setMonto(data.getMonto());
        domain.setMetodoPago(data.getMetodoPago());
        domain.setFechaCreacion(data.getFechaCreacion());
        domain.setFechaActualizacion(data.getFechaActualizacion());
        return domain;
    }
}