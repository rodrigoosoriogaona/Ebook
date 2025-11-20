package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Usuario;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class MapperUsuario {

    public Usuario toUsuario(UsuarioData usuarioData) {
        return new Usuario(
                usuarioData.getId(),
                usuarioData.getNombre(),
                usuarioData.getEmail(),
                usuarioData.getPassword()
        );
    }

    public UsuarioData toData(Usuario usuario) {
        return new UsuarioData(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }

}