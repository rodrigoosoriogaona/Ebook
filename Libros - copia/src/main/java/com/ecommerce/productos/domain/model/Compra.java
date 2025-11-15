package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private Long idCompra;
    private Long transaccionId;
    private String direccionEntrega;
    private String estadoCompra; // PENDIENTE, CONFIRMADA, COMPLETADA
}