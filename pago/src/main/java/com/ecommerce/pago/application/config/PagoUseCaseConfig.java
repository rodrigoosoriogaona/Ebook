package com.ecommerce.pago.application.config;


import com.ecommerce.pago.domain.model.Gateway.PagoGateway;
import com.ecommerce.pago.domain.usecase.PagoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagoUseCaseConfig {

    @Bean
    public PagoUseCase pagoUseCase(PagoGateway pagoGateway) {
        return new PagoUseCase(pagoGateway);
    }
}