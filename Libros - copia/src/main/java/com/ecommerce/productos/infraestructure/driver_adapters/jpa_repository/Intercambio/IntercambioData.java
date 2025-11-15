package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "intercambio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntercambioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIntercambio;

    private Long libroOfrecidoId;
    private Long libroSolicitadoId;
    private Long usuarioOfreceId;
    private Long usuarioSolicitaId;
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}