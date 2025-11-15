package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    private String titulo;
    private String autor;
    private String isbn;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String estado;
    private Long usuarioId;
    private Boolean disponible;
}