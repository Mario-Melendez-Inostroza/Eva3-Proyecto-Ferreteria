package com.ferreteria.bff.service;

import com.ferreteria.bff.client.InventoryClient;
import com.ferreteria.bff.dto.InventoryRequestDto;
import com.ferreteria.bff.dto.InventoryResponseDto;
import com.ferreteria.bff.dto.StockAdjustmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryBffService {

    private final InventoryClient inventoryClient;

    public InventoryResponseDto create(InventoryRequestDto request) {
        return inventoryClient.create(request);
    }

    public List<InventoryResponseDto> findAll() {
        return inventoryClient.findAll();
    }

    public List<InventoryResponseDto> findLowStock() {
        return inventoryClient.findLowStock();
    }

    public InventoryResponseDto findByProduct(UUID productId) {
        return inventoryClient.findByProduct(productId);
    }

    public InventoryResponseDto addStock(UUID productId, StockAdjustmentDto request) {
        return inventoryClient.addStock(productId, request);
    }

    public InventoryResponseDto subtractStock(UUID productId, StockAdjustmentDto request) {
        return inventoryClient.subtractStock(productId, request);
    }
}
