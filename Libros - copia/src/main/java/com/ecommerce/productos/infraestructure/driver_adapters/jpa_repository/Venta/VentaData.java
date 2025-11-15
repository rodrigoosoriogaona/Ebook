package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Venta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    private Long transaccionId;
    private String metodoPago;
    private String direccionEnvio;
    private String estadoEnvio;
}