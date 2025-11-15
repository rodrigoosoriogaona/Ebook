package com.ecommerce.productos.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String password;
}