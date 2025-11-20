package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Publicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long libroId;

    @Column(nullable = false, length = 20)
    private String tipo; // VENTA, INTERCAMBIO

    @Column(nullable = false, length = 20)
    private String estado; // ACTIVA, CERRADA, CANCELADA

    private Double precioVenta;

    @Column(length = 500)
    private String condicionesIntercambio;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;
}