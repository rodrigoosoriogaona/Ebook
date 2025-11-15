package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Compra;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra.CompraData;
import org.springframework.stereotype.Component;

@Component
public class MapperCompra {

    public CompraData toData(Compra compra) {
        CompraData compraData = new CompraData();
        compraData.setIdCompra(compra.getIdCompra());
        compraData.setTransaccionId(compra.getTransaccionId());
        compraData.setDireccionEntrega(compra.getDireccionEntrega());
        compraData.setEstadoCompra(compra.getEstadoCompra());
        return compraData;
    }

    public Compra toDomain(CompraData compraData) {
        Compra compra = new Compra();
        compra.setIdCompra(compraData.getIdCompra());
        compra.setTransaccionId(compraData.getTransaccionId());
        compra.setDireccionEntrega(compraData.getDireccionEntrega());
        compra.setEstadoCompra(compraData.getEstadoCompra());
        return compra;
    }
}