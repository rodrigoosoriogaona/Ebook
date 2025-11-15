package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Transaccion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    private Long libroId;
    private Long compradorId;
    private Long vendedorId;
    private Double precio;
    private Integer cantidad;
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}