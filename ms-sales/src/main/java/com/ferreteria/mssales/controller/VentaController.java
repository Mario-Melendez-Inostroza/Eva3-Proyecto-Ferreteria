package com.ferreteria.mssales.controller;

import com.ferreteria.mssales.dto.*;
import com.ferreteria.mssales.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // POST /sales → registrar una venta (descuenta stock + notifica)
    @PostMapping
    public ResponseEntity<VentaResponseDto> registrar(@Valid @RequestBody VentaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrarVenta(dto));
    }

    // GET /sales → listar todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listar() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    // GET /sales/{id} → buscar una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    // GET /sales/producto/{productId} → ventas de un producto específico
    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<VentaResponseDto>> listarPorProducto(@PathVariable UUID productId) {
        return ResponseEntity.ok(ventaService.listarPorProducto(productId));
    }

    // GET /sales/resumen → total de ventas y monto acumulado
    @GetMapping("/resumen")
    public ResponseEntity<ResumenVentasDto> resumen() {
        return ResponseEntity.ok(ventaService.obtenerResumen());
    }
}
