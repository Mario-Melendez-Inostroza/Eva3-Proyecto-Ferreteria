package com.ferreteria.bff.service;

import com.ferreteria.bff.client.SalesClient;
import com.ferreteria.bff.dto.SaleRequestDto;
import com.ferreteria.bff.dto.SaleResponseDto;
import com.ferreteria.bff.dto.SalesSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesBffService {

    private final SalesClient salesClient;

    public SaleResponseDto register(SaleRequestDto request) {
        return salesClient.register(request);
    }

    public List<SaleResponseDto> findAll() {
        return salesClient.findAll();
    }

    public SaleResponseDto findById(UUID id) {
        return salesClient.findById(id);
    }

    public List<SaleResponseDto> findByProduct(UUID productId) {
        return salesClient.findByProduct(productId);
    }

    public SalesSummaryDto summary() {
        return salesClient.summary();
    }
}
