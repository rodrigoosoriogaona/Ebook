package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Transaccion;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion.TransaccionData;
import org.springframework.stereotype.Component;

@Component
public class MapperTransaccion {

    public TransaccionData toData(Transaccion transaccion) {
        TransaccionData transaccionData = new TransaccionData();
        transaccionData.setIdTransaccion(transaccion.getIdTransaccion());
        transaccionData.setLibroId(transaccion.getLibroId());
        transaccionData.setCompradorId(transaccion.getCompradorId());
        transaccionData.setVendedorId(transaccion.getVendedorId());
        transaccionData.setPrecio(transaccion.getPrecio());
        transaccionData.setCantidad(transaccion.getCantidad());
        transaccionData.setEstado(transaccion.getEstado());
        transaccionData.setFechaCreacion(transaccion.getFechaCreacion());
        transaccionData.setFechaActualizacion(transaccion.getFechaActualizacion());
        return transaccionData;
    }

    public Transaccion toDomain(TransaccionData transaccionData) {
        Transaccion transaccion = new Transaccion();
        transaccion.setIdTransaccion(transaccionData.getIdTransaccion());
        transaccion.setLibroId(transaccionData.getLibroId());
        transaccion.setCompradorId(transaccionData.getCompradorId());
        transaccion.setVendedorId(transaccionData.getVendedorId());
        transaccion.setPrecio(transaccionData.getPrecio());
        transaccion.setCantidad(transaccionData.getCantidad());
        transaccion.setEstado(transaccionData.getEstado());
        transaccion.setFechaCreacion(transaccionData.getFechaCreacion());
        transaccion.setFechaActualizacion(transaccionData.getFechaActualizacion());
        return transaccion;
    }
}