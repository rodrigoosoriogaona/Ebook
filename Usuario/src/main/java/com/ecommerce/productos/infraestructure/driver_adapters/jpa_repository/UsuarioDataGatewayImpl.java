package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.productos.domain.model.Usuario;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioDataGatewayImpl implements UsuarioGateway {


    private final MapperUsuario mapperUsuario;
    private final UsuarioDataJpaRepository repository;

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioData usuarioData = mapperUsuario.toData(usuario);
        return mapperUsuario.toUsuario(repository.save(usuarioData));
    }

    @Override
    public void eliminarPorID(Long id) {

        try{
            repository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorID(Long id) {
        return repository.findById(id)
                .map(usuarioData -> mapperUsuario.toUsuario(usuarioData))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    }
    @Override
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(usuarioData -> mapperUsuario.toUsuario(usuarioData))
                .orElseThrow(() -> new RuntimeException("Correo no encontrado"));
    }


    @Override
    public boolean usuarioExiste(Long usuarioId) {
        return repository.existsById(usuarioId);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {

        UsuarioData usuarioData = mapperUsuario.toData(usuario);

        if (!repository.existsById(usuario.getId())) {
            throw new RuntimeException("Usuario con Id " + usuario.getId() + "no existe");
        }
        return mapperUsuario.toUsuario(repository.save(usuarioData));

    }

}