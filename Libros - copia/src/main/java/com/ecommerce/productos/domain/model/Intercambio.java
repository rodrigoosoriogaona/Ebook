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
public class Intercambio {
    private Long idIntercambio;
    private Long libroOfrecidoId;
    private Long libroSolicitadoId;
    private Long usuarioOfreceId;
    private Long usuarioSolicitaId;
    private String estado; // OFERTADO, ACEPTADO, RECHAZADO, COMPLETADO
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}