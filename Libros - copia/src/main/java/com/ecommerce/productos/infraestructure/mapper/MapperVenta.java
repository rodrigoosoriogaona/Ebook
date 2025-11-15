package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta.VentaData;
import org.springframework.stereotype.Component;

@Component
public class MapperVenta {

    public VentaData toData(Venta venta) {
        VentaData ventaData = new VentaData();
        ventaData.setIdVenta(venta.getIdVenta());
        ventaData.setTransaccionId(venta.getTransaccionId());
        ventaData.setMetodoPago(venta.getMetodoPago());
        ventaData.setDireccionEnvio(venta.getDireccionEnvio());
        ventaData.setEstadoEnvio(venta.getEstadoEnvio());
        return ventaData;
    }

    public Venta toDomain(VentaData ventaData) {
        Venta venta = new Venta();
        venta.setIdVenta(ventaData.getIdVenta());
        venta.setTransaccionId(ventaData.getTransaccionId());
        venta.setMetodoPago(ventaData.getMetodoPago());
        venta.setDireccionEnvio(ventaData.getDireccionEnvio());
        venta.setEstadoEnvio(ventaData.getEstadoEnvio());
        return venta;
    }
}