package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicacion_intercambio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionIntercambioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;

    private Long libroOfrecidoId;
    private Long usuarioPropietarioId;
    private String estado; // ACTIVA, CERRADA

    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}