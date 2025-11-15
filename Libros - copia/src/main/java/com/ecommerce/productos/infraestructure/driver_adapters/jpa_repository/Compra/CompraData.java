package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Compra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    private Long transaccionId;
    private String direccionEntrega;
    private String estadoCompra;
}