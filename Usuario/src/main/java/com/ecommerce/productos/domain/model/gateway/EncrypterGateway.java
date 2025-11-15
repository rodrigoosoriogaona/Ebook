package com.ecommerce.productos.domain.model.gateway;

public interface EncrypterGateway {

    String encrypt (String password);

    boolean checkPass (String passUser, String passBD);
}

