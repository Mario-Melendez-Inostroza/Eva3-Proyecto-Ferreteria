package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.BffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final BffService bffService;

    // POST /api/sales → registrar una venta
    // Flujo interno: verifica producto → descuenta stock → guarda venta → notifica
    @PostMapping
    public ResponseEntity<VentaResponseDto> registrar(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody VentaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bffService.registrarVenta(authHeader, dto));
    }

    // GET /api/sales → listar todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listar(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.listarVentas(authHeader));
    }

    // GET /api/sales/{id} → buscar venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> buscarPorId(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id) {
        return ResponseEntity.ok(bffService.buscarVenta(authHeader, id));
    }

    // GET /api/sales/producto/{productId} → ventas de un producto
    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<VentaResponseDto>> porProducto(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(bffService.ventasPorProducto(authHeader, productId));
    }

    // GET /api/sales/resumen → total ventas y monto acumulado
    @GetMapping("/resumen")
    public ResponseEntity<ResumenVentasDto> resumen(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.resumenVentas(authHeader));
    }
}
