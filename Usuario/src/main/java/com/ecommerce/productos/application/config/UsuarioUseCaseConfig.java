package com.ecommerce.productos.application.config;

import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import com.ecommerce.productos.domain.model.gateway.EncrypterGateway;
import com.ecommerce.productos.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioUseCaseConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioGateway usuarioGateway, EncrypterGateway encrypterGateway) {
        return new UsuarioUseCase(usuarioGateway, encrypterGateway);
    }
}

