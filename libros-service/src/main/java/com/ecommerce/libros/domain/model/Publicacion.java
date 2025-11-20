package com.ecommerce.libros.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publicacion {
    private Long idPublicacion;
    private Long usuarioId;
    private Long libroId;
    private String tipo; // VENTA, INTERCAMBIO
    private String estado; // ACTIVA, CERRADA, CANCELADA
    private Double precioVenta;
    private String condicionesIntercambio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}