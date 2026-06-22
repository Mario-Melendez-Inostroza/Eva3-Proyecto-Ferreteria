package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.ProductBffService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for product management")
public class ProductController {

    private final ProductBffService productBffService;

    @Operation(
            summary = "List products",
            description = "Returns all products available through the product service."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        return ResponseEntity.ok(productBffService.findAll());
    }

    @Operation(
            summary = "Find product by ID",
            description = "Returns a product that matches the provided product identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(productBffService.findById(id));
    }

    @Operation(
            summary = "Search products by name",
            description = "Returns products whose names match the provided search value."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameter"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponseDto>> findByName(
            @Parameter(description = "Product name search value", required = true)
            @RequestParam("nombre") String name) {
        return ResponseEntity.ok(productBffService.findByName(name));
    }

    @Operation(
            summary = "Search products by category",
            description = "Returns products that belong to the provided category."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category value"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(
            @Parameter(description = "Product category", required = true)
            @PathVariable("categoria") String category) {
        return ResponseEntity.ok(productBffService.findByCategory(category));
    }

    @Operation(
            summary = "Create product",
            description = "Creates a new product by forwarding the request to the product service.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data to create",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productBffService.create(dto));
    }

    @Operation(
            summary = "Update product",
            description = "Updates an existing product identified by the provided product ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product data",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productBffService.update(id, dto));
    }

    @Operation(
            summary = "Deactivate product",
            description = "Deactivates a product identified by the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deactivate(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(productBffService.deactivate(id));
    }
}
