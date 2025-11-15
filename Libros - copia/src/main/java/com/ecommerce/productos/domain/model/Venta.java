package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private Long idVenta;
    private Long transaccionId;
    private String metodoPago;
    private String direccionEnvio;
    private String estadoEnvio; // PENDIENTE, ENVIADO, ENTREGADO
}