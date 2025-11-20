package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Usuario;

public interface UsuarioGateway {

    Usuario guardar(Usuario usuario);
    void eliminarPorID (Long id);
    Usuario buscarPorID (Long id);
    Usuario actualizarUsuario(Usuario usuario);
    Usuario buscarPorEmail(String email);

    boolean usuarioExiste(Long usuarioId);
}