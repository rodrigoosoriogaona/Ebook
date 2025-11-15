package com.ecommerce.pago.infraestructure.entry_points;

import com.ecommerce.pago.domain.exception.PagoFallidoException;
import com.ecommerce.pago.domain.exception.PagoNoEncontradoException;
import com.ecommerce.pago.domain.model.Pago;
import com.ecommerce.pago.domain.usecase.PagoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoUseCase pagoUseCase;

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestParam Long transaccionId,
                                          @RequestParam Long usuarioId,
                                          @RequestParam Double monto,
                                          @RequestParam String metodoPago) {
        try {
            Pago pago = pagoUseCase.procesarPago(transaccionId, usuarioId, monto, metodoPago);
            return ResponseEntity.ok(pago);
        } catch (PagoFallidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar pago: " + e.getMessage());
        }
    }

    @PutMapping("/revertir/{pagoId}")
    public ResponseEntity<?> revertirPago(@PathVariable Long pagoId) {
        try {
            Pago pago = pagoUseCase.revertirPago(pagoId);
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