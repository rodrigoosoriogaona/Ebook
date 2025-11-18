package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    private Long idLibro;
    private String titulo;
    private String autor;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String estado; // NUEVO, USADO, etc.
    private Long usuarioId; // Dueño del libro
    private Boolean disponible;
}