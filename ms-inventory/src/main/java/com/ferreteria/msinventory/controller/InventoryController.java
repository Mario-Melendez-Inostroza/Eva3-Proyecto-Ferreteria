package com.ferreteria.msinventory.controller;

import com.ferreteria.msinventory.dto.*;
import com.ferreteria.msinventory.service.InventoryService;
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

/**
 * REST controller exposing inventory management for products, including
 * stock queries and stock adjustments per warehouse.
 */
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Endpoints for inventory management")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Creates an inventory record for a product.
     *
     * @param dto the inventory data to create
     * @return the created inventory record with HTTP 201 status
     */
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
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InventoryResponseDto> crear(@Valid @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.crear(dto));
    }

    /**
     * Lists all inventory records.
     *
     * @return the list of all inventory records
     */
    @Operation(
            summary = "List inventory",
            description = "Returns all inventory records."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory records retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> listar() {
        return ResponseEntity.ok(inventoryService.listarTodo());
    }

    /**
     * Lists inventory records whose stock has fallen below the configured minimum.
     *
     * @return the list of low stock inventory records
     */
    @Operation(
            summary = "List low stock inventory",
            description = "Returns inventory records where stock is below the configured minimum."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low stock inventory records retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventoryResponseDto>> stockBajo() {
        return ResponseEntity.ok(inventoryService.listarStockBajo());
    }

    /**
     * Finds the inventory record associated with a product.
     *
     * @param productId the product unique identifier
     * @return the matching inventory record
     */
    @Operation(
            summary = "Find inventory by product",
            description = "Returns the inventory record associated with the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory record found"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/producto/{productId}")
    public ResponseEntity<InventoryResponseDto> buscarPorProducto(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId) {
        return ResponseEntity.ok(inventoryService.buscarPorProductId(productId));
    }

    /**
     * Increases stock for the product identified by the provided product ID.
     *
     * @param productId the product unique identifier
     * @param dto the stock amount to add
     * @return the updated inventory record
     */
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
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/producto/{productId}/agregar")
    public ResponseEntity<InventoryResponseDto> agregarStock(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(inventoryService.agregarStock(productId, dto));
    }

    /**
     * Decreases stock for the product identified by the provided product ID.
     * Rejects the operation if it would leave the stock in a negative value.
     *
     * @param productId the product unique identifier
     * @param dto the stock amount to subtract
     * @return the updated inventory record
     */
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
            @ApiResponse(responseCode = "400", description = "Invalid stock adjustment request or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/producto/{productId}/descontar")
    public ResponseEntity<InventoryResponseDto> descontarStock(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId,
            @Valid @RequestBody AjustarStockDto dto) {
        return ResponseEntity.ok(inventoryService.descontarStock(productId, dto));
    }
}
