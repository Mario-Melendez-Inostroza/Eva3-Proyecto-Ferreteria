package com.ferreteria.bff.service;

import com.ferreteria.bff.client.ProductClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.ProductRequestDto;
import com.ferreteria.bff.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * BFF service for product operations. Contains no business logic of its
 * own; it simply delegates each call to {@link ProductClient}, the HTTP
 * client for the product microservice.
 */
@Service
@RequiredArgsConstructor
public class ProductBffService {

    private final ProductClient productClient;

    /**
     * Delegates the retrieval of all products to the product microservice.
     *
     * @return the list of products
     */
    public List<ProductResponseDto> findAll() {
        return productClient.findAll();
    }

    /**
     * Delegates the lookup of a product by its identifier to the product
     * microservice.
     *
     * @param id the product unique identifier
     * @return the matching product
     */
    public ProductResponseDto findById(UUID id) {
        return productClient.findById(id);
    }

    /**
     * Delegates the search of products by name to the product microservice.
     *
     * @param name the product name search value
     * @return the list of matching products
     */
    public List<ProductResponseDto> findByName(String name) {
        return productClient.findByName(name);
    }

    /**
     * Delegates the search of products by category to the product
     * microservice.
     *
     * @param category the product category
     * @return the list of matching products
     */
    public List<ProductResponseDto> findByCategory(String category) {
        return productClient.findByCategory(category);
    }

    /**
     * Delegates the creation of a product to the product microservice.
     *
     * @param request the product data to create
     * @return the created product
     */
    public ProductResponseDto create(ProductRequestDto request) {
        return productClient.create(request);
    }

    /**
     * Delegates the update of a product to the product microservice.
     *
     * @param id the product unique identifier
     * @param request the updated product data
     * @return the updated product
     */
    public ProductResponseDto update(UUID id, ProductRequestDto request) {
        return productClient.update(id, request);
    }

    /**
     * Delegates the deactivation of a product to the product microservice.
     *
     * @param id the product unique identifier
     * @return a confirmation message
     */
    public MessageResponseDto deactivate(UUID id) {
        return productClient.deactivate(id);
    }
}
