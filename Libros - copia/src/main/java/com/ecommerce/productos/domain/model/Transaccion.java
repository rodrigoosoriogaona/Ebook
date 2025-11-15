package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {
    private Long idTransaccion;
    private Long libroId;
    private Long compradorId;
    private Long vendedorId;
    private Double precio;
    private Integer cantidad;
    private String estado; // PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}