package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    @Column(nullable = false)
    private Long publicacionId;

    @Column(nullable = false)
    private Long usuarioCompradorId;

    @Column(nullable = false, length = 20)
    private String tipoPago; // VIRTUAL, PRESENCIAL

    @Column(nullable = false, length = 20)
    private String estado; // PENDIENTE, APROBADA, RECHAZADA, COMPLETADA

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 20)
    private String metodoPago; // TARJETA, EFECTIVO, TRANSFERENCIA

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;
}