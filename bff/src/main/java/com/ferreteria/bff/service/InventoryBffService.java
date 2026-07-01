package com.ferreteria.bff.service;

import com.ferreteria.bff.client.InventoryClient;
import com.ferreteria.bff.dto.InventoryRequestDto;
import com.ferreteria.bff.dto.InventoryResponseDto;
import com.ferreteria.bff.dto.StockAdjustmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * BFF service for inventory operations. Contains no business logic of its
 * own; it simply delegates each call to {@link InventoryClient}, the HTTP
 * client for the inventory microservice.
 */
@Service
@RequiredArgsConstructor
public class InventoryBffService {

    private final InventoryClient inventoryClient;

    /**
     * Delegates the creation of an inventory record to the inventory
     * microservice.
     *
     * @param request the inventory data to create
     * @return the created inventory record
     */
    public InventoryResponseDto create(InventoryRequestDto request) {
        return inventoryClient.create(request);
    }

    /**
     * Delegates the retrieval of all inventory records to the inventory
     * microservice.
     *
     * @return the list of inventory records
     */
    public List<InventoryResponseDto> findAll() {
        return inventoryClient.findAll();
    }

    /**
     * Delegates the retrieval of low stock inventory records to the
     * inventory microservice.
     *
     * @return the list of low stock inventory records
     */
    public List<InventoryResponseDto> findLowStock() {
        return inventoryClient.findLowStock();
    }

    /**
     * Delegates the lookup of an inventory record by product to the
     * inventory microservice.
     *
     * @param productId the product unique identifier
     * @return the matching inventory record
     */
    public InventoryResponseDto findByProduct(UUID productId) {
        return inventoryClient.findByProduct(productId);
    }

    /**
     * Delegates a stock increase for a product to the inventory
     * microservice.
     *
     * @param productId the product unique identifier
     * @param request the stock amount to add
     * @return the updated inventory record
     */
    public InventoryResponseDto addStock(UUID productId, StockAdjustmentDto request) {
        return inventoryClient.addStock(productId, request);
    }

    /**
     * Delegates a stock decrease for a product to the inventory
     * microservice.
     *
     * @param productId the product unique identifier
     * @param request the stock amount to subtract
     * @return the updated inventory record
     */
    public InventoryResponseDto subtractStock(UUID productId, StockAdjustmentDto request) {
        return inventoryClient.subtractStock(productId, request);
    }
}
