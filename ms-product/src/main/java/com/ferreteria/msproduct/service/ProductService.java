package com.ferreteria.msproduct.service;

import com.ferreteria.msproduct.dto.*;
import java.util.List;
import java.util.UUID;

/**
 * Business operations for browsing, creating, updating, and deactivating
 * products in the hardware store catalog.
 */
public interface ProductService {

    /**
     * Lists all active products in the catalog.
     *
     * @return the list of active products
     */
    List<ProductResponseDto> listarActivos();

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the product unique identifier
     * @return the matching product
     */
    ProductResponseDto buscarPorId(UUID id);

    /**
     * Searches active products whose names contain the given value.
     *
     * @param nombre the product name search value
     * @return the list of matching active products
     */
    List<ProductResponseDto> buscarPorNombre(String nombre);

    /**
     * Searches active products belonging to the given category.
     *
     * @param categoria the product category
     * @return the list of matching active products
     */
    List<ProductResponseDto> buscarPorCategoria(String categoria);

    /**
     * Creates a new product, validating that its name is unique.
     *
     * @param dto the product data to create
     * @return the newly created product
     */
    ProductResponseDto crear(ProductRequestDto dto);

    /**
     * Updates an existing product identified by its unique identifier.
     *
     * @param id  the product unique identifier
     * @param dto the updated product data
     * @return the updated product
     */
    ProductResponseDto actualizar(UUID id, ProductRequestDto dto);

    /**
     * Deactivates an existing product by its unique identifier. This is a
     * soft delete: the record is not physically removed from the catalog.
     *
     * @param id the product unique identifier
     * @return a confirmation message
     */
    MessageResponseDto desactivar(UUID id);
}
