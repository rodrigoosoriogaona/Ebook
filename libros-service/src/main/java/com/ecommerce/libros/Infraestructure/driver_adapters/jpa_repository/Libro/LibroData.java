package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Libro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(length = 50)
    private String genero;

    @Column(length = 1000)
    private String sinopsis;

    @Column(nullable = false, length = 20)
    private String estadoFisico;

    private Double precio;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(nullable = false)
    private Boolean enVenta = false;

    @Column(nullable = false)
    private Boolean enIntercambio = false;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;
}