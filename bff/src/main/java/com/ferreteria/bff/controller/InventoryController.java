package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.InventoryBffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Inventory", description = "Endpoints for inventory management")
public class InventoryController {

    private final InventoryBffService inventoryBffService;

    @Operation(
            summary = "Create inventory record",
            description = "Creates an inventory record for a product.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Inventory data to create",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid inventory request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryBffService.create(dto));
    }

    @Operation(
            summary = "List inventory",
            description = "Returns all inventory records."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory records retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> findAll() {
        return ResponseEntity.ok(inventoryBffService.findAll());
    }

    @Operation(
            summary = "List low stock inventory",
            description = "Returns inventory records where stock is below the configured minimum."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low stock inventory records retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventoryResponseDto>> findLowStock() {
        return ResponseEntity.ok(inventoryBffService.findLowStock());
    }

    @Operation(
            summary = "Find inventory by product",
            description = "Returns the inventory record associated with the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory record found"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/producto/{productId}")
    public ResponseEntity<InventoryResponseDto> findByProduct(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId) {
        return ResponseEntity.ok(inventoryBffService.findByProduct(productId));
    }

    @Operation(
            summary = "Add stock",
            description = "Increases stock for the product identified by the provided product ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Stock amount to add",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock increased successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock adjustment request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/producto/{productId}/agregar")
    public ResponseEntity<InventoryResponseDto> addStock(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId,
            @Valid @RequestBody StockAdjustmentDto dto) {
        return ResponseEntity.ok(inventoryBffService.addStock(productId, dto));
    }

    @Operation(
            summary = "Subtract stock",
            description = "Decreases stock for the product identified by the provided product ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Stock amount to subtract",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock decreased successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock adjustment request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/producto/{productId}/descontar")
    public ResponseEntity<InventoryResponseDto> subtractStock(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId,
            @Valid @RequestBody StockAdjustmentDto dto) {
        return ResponseEntity.ok(inventoryBffService.subtractStock(productId, dto));
    }
}
