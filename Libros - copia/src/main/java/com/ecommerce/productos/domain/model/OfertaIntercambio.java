package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfertaIntercambio {
    private Long idOferta;
    private Long intercambioId;
    private String mensaje;
    private Boolean aceptada;
}