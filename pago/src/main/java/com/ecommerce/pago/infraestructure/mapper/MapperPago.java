package com.ecommerce.pago.infraestructure.mapper;

import com.ecommerce.pago.domain.model.Pago;
import com.ecommerce.pago.infraestructure.drive_adapters.jpa_repository.PagoData;
import org.springframework.stereotype.Component;

@Component
public class MapperPago {

    public PagoData toData(Pago pago) {
        PagoData pagoData = new PagoData();
        pagoData.setIdPago(pago.getIdPago());
        pagoData.setTransaccionId(pago.getTransaccionId());
        pagoData.setUsuarioId(pago.getUsuarioId());
        pagoData.setMonto(pago.getMonto());
        pagoData.setEstado(pago.getEstado());
        pagoData.setMetodoPago(pago.getMetodoPago());
        pagoData.setReferencia(pago.getReferencia());
        pagoData.setFechaCreacion(pago.getFechaCreacion());
        pagoData.setFechaActualizacion(pago.getFechaActualizacion());
        return pagoData;
    }

    public Pago toDomain(PagoData pagoData) {
        Pago pago = new Pago();
        pago.setIdPago(pagoData.getIdPago());
        pago.setTransaccionId(pagoData.getTransaccionId());
        pago.setUsuarioId(pagoData.getUsuarioId());
        pago.setMonto(pagoData.getMonto());
        pago.setEstado(pagoData.getEstado());
        pago.setMetodoPago(pagoData.getMetodoPago());
        pago.setReferencia(pagoData.getReferencia());
        pago.setFechaCreacion(pagoData.getFechaCreacion());
        pago.setFechaActualizacion(pagoData.getFechaActualizacion());
        return pago;
    }
}