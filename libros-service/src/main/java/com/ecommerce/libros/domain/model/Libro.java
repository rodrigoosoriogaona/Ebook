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
public class Libro {
    private Long idLibro;
    private Long usuarioId;
    private String titulo;
    private String autor;
    private String genero;
    private String sinopsis;
    private String estadoFisico; // NUEVO, USADO, EXCELENTE
    private Double precio;
    private Boolean disponible;
    private Boolean enVenta;
    private Boolean enIntercambio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}