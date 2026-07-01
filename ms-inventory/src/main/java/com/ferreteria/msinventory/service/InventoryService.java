package com.ferreteria.msinventory.service;

import com.ferreteria.msinventory.dto.*;
import java.util.List;
import java.util.UUID;

/**
 * Business operations for managing product inventory, including stock
 * queries and stock adjustments per warehouse.
 */
public interface InventoryService {

    /**
     * Creates an inventory record for a product.
     *
     * @param dto the inventory data to create
     * @return the created inventory record
     */
    InventoryResponseDto crear(InventoryRequestDto dto);

    /**
     * Finds the inventory record associated with a product.
     *
     * @param productId the product unique identifier
     * @return the matching inventory record
     */
    InventoryResponseDto buscarPorProductId(UUID productId);

    /**
     * Lists all inventory records.
     *
     * @return the list of all inventory records
     */
    List<InventoryResponseDto> listarTodo();

    /**
     * Lists inventory records whose stock has fallen below the configured minimum.
     *
     * @return the list of low stock inventory records
     */
    List<InventoryResponseDto> listarStockBajo();

    /**
     * Increases stock for the product identified by the provided product ID.
     *
     * @param productId the product unique identifier
     * @param dto the stock amount to add
     * @return the updated inventory record
     */
    InventoryResponseDto agregarStock(UUID productId, AjustarStockDto dto);

    /**
     * Decreases stock for the product identified by the provided product ID.
     * Rejects the operation if it would leave the stock in a negative value.
     *
     * @param productId the product unique identifier
     * @param dto the stock amount to subtract
     * @return the updated inventory record
     */
    InventoryResponseDto descontarStock(UUID productId, AjustarStockDto dto);
}
