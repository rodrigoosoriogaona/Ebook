package com.ecommerce.pago.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    private Long idPago;
    private Long transaccionId;
    private Long usuarioId;
    private Double monto;
    private String estado;
    private String metodoPago;
    private String tipoPago;       // VIRTUAL, PRESENCIAL
    private String referencia;
    private Long publicacionId;    // ID de la publicación de libros
    private String motivoReversion;// motivo de reversión
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}