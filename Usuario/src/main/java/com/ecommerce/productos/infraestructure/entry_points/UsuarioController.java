package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.model.Usuario;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import com.ecommerce.productos.domain.usecase.UsuarioUseCase;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import com.ecommerce.productos.infraestructure.mapper.MapperUsuario;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController //indica que esta clase es un controlador, y se van a crear APIs que se van a exponer
@RequestMapping("/api/ecommerce/usuario") //parametrizar URLs
@RequiredArgsConstructor //crea constructores
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final MapperUsuario mapperUsuario;
    private final UsuarioGateway usuarioGateway;

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody UsuarioData usuarioData) {

        Usuario usuario = mapperUsuario.toUsuario(usuarioData);
        Usuario usuarioValidadoGuardado = usuarioUseCase.guardarUsuario(usuario);

        if (usuarioValidadoGuardado.getId() != null) {
            return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.CONFLICT);
    }
    @GetMapping("/{id}")

    public ResponseEntity<Usuario> findByIdUsuario(@PathVariable Long id) {

        Usuario usuarioValidadoEncontrado = usuarioUseCase.buscarPorIdUsuario(id);
        if (usuarioValidadoEncontrado.getId() != null) {
            return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.OK);
        }
        return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByIdUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioUseCase.buscarPorIdUsuario(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El usario con " + id + "no exite en la BD");
            }
            usuarioUseCase.eliminarPorIdUsuario(id);

            return ResponseEntity.ok().body("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @PutMapping("/update")

    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioData usuarioData) {
        try {
            Usuario usuario = mapperUsuario.toUsuario(usuarioData);
            Usuario usuarioValidadoActualizado = usuarioUseCase.actualizarUsuario(usuario);
            return new ResponseEntity<>(usuarioValidadoActualizado, HttpStatus.OK);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        Usuario usuario = usuarioGateway.buscarPorEmail(email);

        if (usuario == null || !usuarioUseCase.login(usuario, password).equals("Login exitoso")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login incorrecto, verificar correo y contraseña");
        }

        return ResponseEntity.ok(usuario);  //  RETORNAMOS EL USUARIO COMPLETO
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/buscarPorEmail")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioGateway.buscarPorEmail(email);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(usuario);
    }

}