package com.ecommerce.productos.application.config;

import com.ecommerce.productos.domain.model.gateway.*;
import com.ecommerce.productos.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransaccionUseCaseConfig {

    @Bean
    public LibroUseCase libroUseCase(LibroGateway libroGateway, UsuarioGateway usuarioGateway) {
        return new LibroUseCase(libroGateway, usuarioGateway);
    }

    @Bean
    public TransaccionUseCase transaccionUseCase(TransaccionGateway transaccionGateway,
                                                 LibroUseCase libroUseCase,
                                                 UsuarioGateway usuarioGateway,
                                                 PagoGateway pagoGateway) {
        return new TransaccionUseCase(transaccionGateway, libroUseCase, usuarioGateway, pagoGateway);
    }

    @Bean
    public IntercambioUseCase intercambioUseCase(IntercambioGateway intercambioGateway,
                                                 LibroUseCase libroUseCase,
                                                 UsuarioGateway usuarioGateway) {
        return new IntercambioUseCase(intercambioGateway, libroUseCase, usuarioGateway);
    }

    @Bean
    public VentaUseCase ventaUseCase(VentaGateway ventaGateway, TransaccionUseCase transaccionUseCase) {
        return new VentaUseCase(ventaGateway, transaccionUseCase);
    }

    @Bean
    public CompraUseCase compraUseCase(CompraGateway compraGateway, TransaccionUseCase transaccionUseCase) {
        return new CompraUseCase(compraGateway, transaccionUseCase);
    }

    @Bean
    public OfertaUseCase ofertaUseCase(OfertaGateway ofertaGateway, IntercambioUseCase intercambioUseCase) {
        return new OfertaUseCase(ofertaGateway, intercambioUseCase);
    }
}