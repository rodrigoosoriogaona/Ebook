package com.ecommerce.libros.Infraestructure.driver_adapters.external_repository;

import com.ecommerce.libros.domain.model.Gateway.PagoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class PagoGatewayImpl implements PagoGateway {

    private final RestTemplate restTemplate;
    private final String PAGO_SERVICE_URL = "http://localhost:9093/api/ecommerce/pagos";

    @Override
    public boolean procesarPago(Long usuarioId, Double monto, String metodoPago) {
        try {
            ProcesarPagoRequest request = new ProcesarPagoRequest();
            request.setUsuarioId(usuarioId);
            request.setMonto(monto);
            request.setMetodoPago(metodoPago);
            request.setTipoPago("VIRTUAL");
            request.setTransaccionId(1L);
            request.setPublicacionId(1L);

            ResponseEntity<Pago> response = restTemplate.postForEntity(
                    PAGO_SERVICE_URL + "/procesar",
                    request,
                    Pago.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Pago pago = response.getBody();
                return "COMPLETADO".equals(pago.getEstado());
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean reversarPago(Long usuarioId, Double monto) {
        try {
            // Por ahora, simulación simple
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @lombok.Data
    public static class ProcesarPagoRequest {
        private Long transaccionId;
        private Long usuarioId;
        private Double monto;
        private String metodoPago;
        private String tipoPago;
        private Long publicacionId;
    }

    @lombok.Data
    public static class Pago {
        private Long idPago;
        private String estado;
    }
}