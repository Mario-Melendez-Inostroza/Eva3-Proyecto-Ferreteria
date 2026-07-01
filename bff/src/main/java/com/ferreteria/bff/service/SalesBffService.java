package com.ferreteria.bff.service;

import com.ferreteria.bff.client.SalesClient;
import com.ferreteria.bff.dto.SaleRequestDto;
import com.ferreteria.bff.dto.SaleResponseDto;
import com.ferreteria.bff.dto.SalesSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * BFF service for sales operations. Contains no business logic of its own;
 * it simply delegates each call to {@link SalesClient}, the HTTP client for
 * the sales microservice.
 */
@Service
@RequiredArgsConstructor
public class SalesBffService {

    private final SalesClient salesClient;

    /**
     * Delegates the registration of a sale to the sales microservice.
     *
     * @param request the sale data to register
     * @return the registered sale
     */
    public SaleResponseDto register(SaleRequestDto request) {
        return salesClient.register(request);
    }

    /**
     * Delegates the retrieval of all sales to the sales microservice.
     *
     * @return the list of sales
     */
    public List<SaleResponseDto> findAll() {
        return salesClient.findAll();
    }

    /**
     * Delegates the lookup of a sale by its identifier to the sales
     * microservice.
     *
     * @param id the sale unique identifier
     * @return the matching sale
     */
    public SaleResponseDto findById(UUID id) {
        return salesClient.findById(id);
    }

    /**
     * Delegates the retrieval of sales by product to the sales
     * microservice.
     *
     * @param productId the product unique identifier
     * @return the list of matching sales
     */
    public List<SaleResponseDto> findByProduct(UUID productId) {
        return salesClient.findByProduct(productId);
    }

    /**
     * Delegates the retrieval of the sales summary to the sales
     * microservice.
     *
     * @return the sales summary
     */
    public SalesSummaryDto summary() {
        return salesClient.summary();
    }
}
