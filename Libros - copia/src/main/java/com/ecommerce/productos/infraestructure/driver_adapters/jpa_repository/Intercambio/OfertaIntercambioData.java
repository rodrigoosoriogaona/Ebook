package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oferta_intercambio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfertaIntercambioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOferta;

    private Long intercambioId;
    private String mensaje;
    private Boolean aceptada;
}