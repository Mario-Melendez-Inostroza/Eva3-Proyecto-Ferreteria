package com.ferreteria.mssales.service;

import com.ferreteria.mssales.dto.*;
import java.util.List;
import java.util.UUID;

/**
 * Business operations for registering sales and querying sale history,
 * orchestrating product validation, inventory stock updates, and
 * notifications across microservices.
 */
public interface VentaService {

    /**
     * Registers a new sale after validating the product against ms-product
     * and discounting stock via ms-inventory. Also triggers a best-effort
     * notification to ms-notification when stock is left low or once the
     * sale is registered; failures in that notification do not affect the
     * sale outcome.
     *
     * @param dto the sale data to register
     * @return the registered sale detail
     */
    VentaResponseDto registrarVenta(VentaRequestDto dto);

    /**
     * Lists all registered sales, most recent first.
     *
     * @return the list of all sales
     */
    List<VentaResponseDto> listarTodas();

    /**
     * Lists sales associated with the given product, most recent first.
     *
     * @param productId the product unique identifier
     * @return the list of sales for the product
     */
    List<VentaResponseDto> listarPorProducto(UUID productId);

    /**
     * Finds a sale by its unique identifier.
     *
     * @param id the sale unique identifier
     * @return the matching sale detail
     */
    VentaResponseDto buscarPorId(UUID id);

    /**
     * Obtains an aggregated summary of all sales.
     *
     * @return the total number of sales and the accumulated sales amount
     */
    ResumenVentasDto obtenerResumen();
}
