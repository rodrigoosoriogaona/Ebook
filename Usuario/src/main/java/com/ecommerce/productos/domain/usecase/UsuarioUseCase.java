package com.ecommerce.productos.domain.usecase;


import com.ecommerce.productos.domain.model.Usuario;
import com.ecommerce.productos.domain.model.gateway.EncrypterGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor


public class UsuarioUseCase {


    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;

    public String login(Usuario usuario, String password) {
        if (!encrypterGateway.checkPass(password, usuario.getPassword())) {
            return "Login incorrecto, intentar con otra contraseña";
        }

        return "Login exitoso";
    }

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario.getEmail() == null && usuario.getPassword() == null) {

            throw new NullPointerException("Ojo con eso manito - guardarUsuario");
        }
        usuario.setPassword(encrypterGateway.encrypt(usuario.getPassword()));
        return usuarioGateway.guardar(usuario);

    }

    public void eliminarPorIdUsuario(Long id){
        try{
            usuarioGateway.eliminarPorID(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Usuario buscarPorIdUsuario(Long id){


        try {
            return usuarioGateway.buscarPorID(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;

        }
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if(usuario.getId() == null) {//se hace por si no encuentra el usuario en la BD
            throw new IllegalArgumentException("El id es obligatorio para actualizar");
        }
        if (usuario.getPassword()!=null && !usuario.getPassword().isEmpty()){
            String passwordEncrypt= encrypterGateway.encrypt(usuario.getPassword());
            usuario.setPassword(passwordEncrypt);
        }

        return usuarioGateway.actualizarUsuario(usuario);
    }
}
