package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Oferta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ofertas_intercambio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfertaIntercambioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOferta;

    @Column(nullable = false)
    private Long publicacionId;

    @Column(nullable = false)
    private Long usuarioOfertanteId;

    @Column(nullable = false)
    private Long libroOfertadoId;

    @Column(nullable = false, length = 20)
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA

    @Column(length = 500)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;
}