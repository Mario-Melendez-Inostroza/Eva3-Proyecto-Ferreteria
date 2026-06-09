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
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final BffService bffService;

    // POST /api/inventory → crear registro de inventario para un producto
    @PostMapping
    public ResponseEntity<InventoryResponseDto> crear(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bffService.crearInventario(authHeader, dto));
    }

    // GET /api/inventory → listar todo el inventario
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> listar(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.listarInventario(authHeader));
    }

    // GET /api/inventory/stock-bajo → productos con stock bajo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventoryResponseDto>> stockBajo(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.stockBajo(authHeader));
    }

    // GET /api/inventory/producto/{productId} → stock de un producto
    @GetMapping("/producto/{productId}")
    public ResponseEntity<InventoryResponseDto> buscarPorProducto(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(bffService.buscarInventarioPorProducto(authHeader, productId));
    }

    // PUT /api/inventory/producto/{productId}/agregar → sumar stock
    @PutMapping("/producto/{productId}/agregar")
    public ResponseEntity<InventoryResponseDto> agregarStock(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(bffService.agregarStock(authHeader, productId, dto));
    }

    // PUT /api/inventory/producto/{productId}/descontar → restar stock
    @PutMapping("/producto/{productId}/descontar")
    public ResponseEntity<InventoryResponseDto> descontarStock(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(bffService.descontarStock(authHeader, productId, dto));
    }
}
