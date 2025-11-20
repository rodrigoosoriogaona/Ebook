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
public class OfertaIntercambio {
    private Long idOferta;
    private Long publicacionId;
    private Long usuarioOfertanteId;
    private Long libroOfertadoId;
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}