package com.ecommerce.productos.infraestructure.security;

import com.ecommerce.productos.domain.model.gateway.EncrypterGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service

public class EncrypterGatewayImpl implements EncrypterGateway {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encrypt(String password) {
        return encoder.encode(password);
    }

    public boolean checkPass(String passUser, String passBD) {
        return encoder.matches(passUser, passBD);
    }

}