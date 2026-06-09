package com.ferreteria.msinventory.controller;

import com.ferreteria.msinventory.dto.*;
import com.ferreteria.msinventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // POST /inventory → crear registro de inventario para un producto
    @PostMapping
    public ResponseEntity<InventoryResponseDto> crear(@Valid @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.crear(dto));
    }

    // GET /inventory → listar todo el inventario
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> listar() {
        return ResponseEntity.ok(inventoryService.listarTodo());
    }

    // GET /inventory/stock-bajo → productos con stock bajo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventoryResponseDto>> stockBajo() {
        return ResponseEntity.ok(inventoryService.listarStockBajo());
    }

    // GET /inventory/producto/{productId} → stock de un producto específico
    @GetMapping("/producto/{productId}")
    public ResponseEntity<InventoryResponseDto> buscarPorProducto(@PathVariable UUID productId) {
        return ResponseEntity.ok(inventoryService.buscarPorProductId(productId));
    }

    // PUT /inventory/producto/{productId}/agregar → sumar stock
    @PutMapping("/producto/{productId}/agregar")
    public ResponseEntity<InventoryResponseDto> agregarStock(
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(inventoryService.agregarStock(productId, dto));
    }

    // PUT /inventory/producto/{productId}/descontar → restar stock (lo llama ms-sales)
    @PutMapping("/producto/{productId}/descontar")
    public ResponseEntity<InventoryResponseDto> descontarStock(
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(inventoryService.descontarStock(productId, dto));
    }
}
