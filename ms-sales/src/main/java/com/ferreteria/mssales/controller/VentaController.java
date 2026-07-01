package com.ferreteria.mssales.controller;

import com.ferreteria.mssales.dto.*;
import com.ferreteria.mssales.service.VentaService;
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
 * REST controller exposing sale registration and sale query operations for
 * the sales microservice.
 */
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Endpoints for sales management")
public class VentaController {

    private final VentaService ventaService;

    /**
     * Registers a new sale, validating the product against ms-product,
     * discounting stock via ms-inventory, and notifying ms-notification when
     * applicable.
     *
     * @param dto the sale data to register
     * @return the registered sale detail with HTTP 201 status
     */
    @Operation(
            summary = "Register sale",
            description = "Registers a new sale, subtracts inventory stock, and sends notifications when applicable.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sale data to register",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sale request or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Product or inventory record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<VentaResponseDto> registrar(@Valid @RequestBody VentaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrarVenta(dto));
    }

    /**
     * Lists all registered sales.
     *
     * @return the list of all sales
     */
    @Operation(
            summary = "List sales",
            description = "Returns all registered sales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listar() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    /**
     * Finds a sale by its unique identifier.
     *
     * @param id the sale unique identifier
     * @return the matching sale detail
     */
    @Operation(
            summary = "Find sale by ID",
            description = "Returns a sale that matches the provided sale identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "400", description = "Invalid sale identifier"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> buscarPorId(
            @Parameter(description = "Sale unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    /**
     * Lists sales associated with the given product.
     *
     * @param productId the product unique identifier
     * @return the list of sales for the product
     */
    @Operation(
            summary = "List sales by product",
            description = "Returns sales associated with the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<VentaResponseDto>> listarPorProducto(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId) {
        return ResponseEntity.ok(ventaService.listarPorProducto(productId));
    }

    /**
     * Gets an aggregated summary of all sales.
     *
     * @return the total number of sales and the accumulated sales amount
     */
    @Operation(
            summary = "Get sales summary",
            description = "Returns the total number of sales and the accumulated sales amount."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales summary retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/resumen")
    public ResponseEntity<ResumenVentasDto> resumen() {
        return ResponseEntity.ok(ventaService.obtenerResumen());
    }
}
