package com.ecommerce.libros.application.config;

import com.ecommerce.libros.domain.model.Gateway.*;
import com.ecommerce.libros.domain.UseCase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public LibroUseCase libroUseCase(LibroGateway libroGateway, UsuarioGateway usuarioGateway) {
        return new LibroUseCase(libroGateway, usuarioGateway);
    }

    @Bean
    public PublicacionUseCase publicacionUseCase(PublicacionGateway publicacionGateway,
                                                 LibroUseCase libroUseCase,
                                                 UsuarioGateway usuarioGateway) {
        return new PublicacionUseCase(publicacionGateway, libroUseCase, usuarioGateway);
    }

    @Bean
    public OfertaUseCase ofertaUseCase(OfertaGateway ofertaGateway,
                                       PublicacionGateway publicacionGateway,
                                       LibroUseCase libroUseCase,
                                       UsuarioGateway usuarioGateway) {
        return new OfertaUseCase(ofertaGateway, publicacionGateway, libroUseCase, usuarioGateway);
    }

    @Bean
    public TransaccionUseCase transaccionUseCase(TransaccionGateway transaccionGateway,
                                                 PublicacionGateway publicacionGateway,
                                                 LibroUseCase libroUseCase,
                                                 UsuarioGateway usuarioGateway,
                                                 PagoGateway pagoGateway) {
        return new TransaccionUseCase(transaccionGateway, publicacionGateway, libroUseCase, usuarioGateway, pagoGateway);
    }
}