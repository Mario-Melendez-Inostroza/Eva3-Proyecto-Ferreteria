package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.SalesBffService;
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
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Endpoints for sales management")
public class SalesController {

    private final SalesBffService salesBffService;

    @Operation(
            summary = "Register sale",
            description = "Registers a new sale by forwarding the request to the sales service.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sale data to register",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sale request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<SaleResponseDto> register(@Valid @RequestBody SaleRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salesBffService.register(dto));
    }

    @Operation(
            summary = "List sales",
            description = "Returns all registered sales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> findAll() {
        return ResponseEntity.ok(salesBffService.findAll());
    }

    @Operation(
            summary = "Find sale by ID",
            description = "Returns a sale that matches the provided sale identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "400", description = "Invalid sale identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> findById(
            @Parameter(description = "Sale unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(salesBffService.findById(id));
    }

    @Operation(
            summary = "List sales by product",
            description = "Returns sales associated with the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<SaleResponseDto>> findByProduct(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID productId) {
        return ResponseEntity.ok(salesBffService.findByProduct(productId));
    }

    @Operation(
            summary = "Get sales summary",
            description = "Returns the total number of sales and the accumulated sales amount."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales summary retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/resumen")
    public ResponseEntity<SalesSummaryDto> summary() {
        return ResponseEntity.ok(salesBffService.summary());
    }
}
