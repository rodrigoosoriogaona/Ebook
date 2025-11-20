package com.ecommerce.pago.infraestructure.entry_points;

import com.ecommerce.pago.domain.exception.PagoFallidoException;
import com.ecommerce.pago.domain.exception.PagoNoEncontradoException;
import com.ecommerce.pago.domain.model.Pago;
import com.ecommerce.pago.domain.usecase.PagoUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoUseCase pagoUseCase;

    // DTOs para requests
    @Data
    public static class ProcesarPagoRequest {
        private Long transaccionId;
        private Long usuarioId;
        private Double monto;
        private String metodoPago;
        private String tipoPago;     // NUEVO
        private Long publicacionId;  // NUEVO
    }

    @Data
    public static class RevertirPagoRequest {
        private String motivo; // NUEVO
    }

    //  Usar @RequestBody en lugar de @RequestParam
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestBody ProcesarPagoRequest request) {
        try {
            Pago pago = pagoUseCase.procesarPago(
                    request.getTransaccionId(),
                    request.getUsuarioId(),
                    request.getMonto(),
                    request.getMetodoPago(),
                    request.getTipoPago(),     // NUEVO
                    request.getPublicacionId() // NUEVO
            );
            return ResponseEntity.ok(pago);
        } catch (PagoFallidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar pago: " + e.getMessage());
        }
    }

    //Agregar motivo de reversión
    @PutMapping("/revertir/{pagoId}")
    public ResponseEntity<?> revertirPago(@PathVariable Long pagoId,
                                          @RequestBody RevertirPagoRequest request) {
        try {
            Pago pago = pagoUseCase.revertirPago(pagoId, request.getMotivo());
            return ResponseEntity.ok(pago);
        } catch (PagoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al revertir pago: " + e.getMessage());
        }
    }


    @GetMapping("/{pagoId}")
    public ResponseEntity<?> consultarPago(@PathVariable Long pagoId) {
        try {
            Pago pago = pagoUseCase.consultarPago(pagoId);
            return ResponseEntity.ok(pago);
        } catch (PagoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar pago: " + e.getMessage());
        }
    }

    @GetMapping("/transaccion/{transaccionId}")
    public ResponseEntity<?> consultarPagoPorTransaccion(@PathVariable Long transaccionId) {
        try {
            Pago pago = pagoUseCase.consultarPagoPorTransaccion(transaccionId);
            return ResponseEntity.ok(pago);
        } catch (PagoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar pago: " + e.getMessage());
        }
    }
}