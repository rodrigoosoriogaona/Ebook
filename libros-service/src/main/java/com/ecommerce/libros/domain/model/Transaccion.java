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
public class Transaccion {
    private Long idTransaccion;
    private Long publicacionId;
    private Long usuarioCompradorId;
    private String tipoPago; // VIRTUAL, PRESENCIAL
    private String estado; // PENDIENTE, APROBADA, RECHAZADA, COMPLETADA
    private Double monto;
    private String metodoPago; // TARJETA, EFECTIVO, TRANSFERENCIA
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}