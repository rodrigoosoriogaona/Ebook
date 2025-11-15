package com.ecommerce.productos.infraestructure.driver_adapters.external_repository;


import com.ecommerce.productos.domain.model.gateway.PagoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class PagoGatewayImpl implements PagoGateway {
    private final RestTemplate restTemplate;

    @Override
    public boolean procesarPago(Long usuarioId, Double monto) {
        try {
            String url = "http://localhost:9093/api/ecommerce/pagos/procesar" +
                    "?usuarioId=" + usuarioId + "&monto=" + monto + "&metodoPago=TARJETA";

            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean revertirPago(String idTransaccionPago) {
        try {
            String url = "http://localhost:9093/api/ecommerce/pagos/revertir/" + idTransaccionPago;
            restTemplate.put(url, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}