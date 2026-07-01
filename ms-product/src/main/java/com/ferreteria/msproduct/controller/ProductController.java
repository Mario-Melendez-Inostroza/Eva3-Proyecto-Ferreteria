package com.ferreteria.msproduct.controller;

import com.ferreteria.msproduct.dto.*;
import com.ferreteria.msproduct.service.ProductService;
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
 * REST controller exposing operations to browse, create, update, and
 * deactivate products in the hardware store catalog.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for product catalog management")
public class ProductController {

    private final ProductService productService;

    /**
     * Lists all active products in the catalog.
     *
     * @return the list of active products
     */
    @Operation(
            summary = "List active products",
            description = "Returns all active products in the catalog."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> listar() {
        return ResponseEntity.ok(productService.listarActivos());
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the product unique identifier
     * @return the matching product
     */
    @Operation(
            summary = "Find product by ID",
            description = "Returns product information using its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> buscarPorId(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(productService.buscarPorId(id));
    }

    /**
     * Searches active products whose names contain the given value.
     *
     * @param nombre the product name search value
     * @return the list of matching active products
     */
    @Operation(
            summary = "Search products by name",
            description = "Returns active products whose names contain the provided value."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponseDto>> buscarPorNombre(
            @Parameter(description = "Product name search value", required = true)
            @RequestParam String nombre) {
        return ResponseEntity.ok(productService.buscarPorNombre(nombre));
    }

    /**
     * Searches active products belonging to the given category.
     *
     * @param categoria the product category
     * @return the list of matching active products
     */
    @Operation(
            summary = "Search products by category",
            description = "Returns active products that belong to the provided category."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductResponseDto>> buscarPorCategoria(
            @Parameter(description = "Product category", required = true)
            @PathVariable String categoria) {
        return ResponseEntity.ok(productService.buscarPorCategoria(categoria));
    }

    /**
     * Creates a new product in the catalog.
     *
     * @param dto the product data to create
     * @return the newly created product
     */
    @Operation(
            summary = "Create product",
            description = "Registers a new product in the catalog.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data to create",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> crear(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.crear(dto));
    }

    /**
     * Updates an existing product identified by its unique identifier.
     *
     * @param id  the product unique identifier
     * @param dto the updated product data
     * @return the updated product
     */
    @Operation(
            summary = "Update product",
            description = "Updates an existing product by its unique identifier.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product data",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> actualizar(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.actualizar(id, dto));
    }

    /**
     * Deactivates an existing product by its unique identifier. This is a
     * soft delete: the record is not physically removed from the catalog.
     *
     * @param id the product unique identifier
     * @return a confirmation message
     */
    @Operation(
            summary = "Deactivate product",
            description = "Marks an existing product as inactive by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product identifier"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> desactivar(
            @Parameter(description = "Product unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(productService.desactivar(id));
    }
}
