package com.ferreteria.bff.service;

import com.ferreteria.bff.client.InventoryClient;
import com.ferreteria.bff.dto.InventoryRequestDto;
import com.ferreteria.bff.dto.InventoryResponseDto;
import com.ferreteria.bff.dto.StockAdjustmentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryBffServiceTest {

    @Mock
    private InventoryClient inventoryClient;

    @Test
    void createShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        InventoryRequestDto request = new InventoryRequestDto(UUID.randomUUID(), 10, 5, "Principal");
        when(inventoryClient.create(request)).thenReturn(response(request.productId(), 10));

        InventoryResponseDto result = service.create(request);

        assertEquals(10, result.stock());
    }

    @Test
    void findAllShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        when(inventoryClient.findAll()).thenReturn(List.of(response(UUID.randomUUID(), 8)));

        List<InventoryResponseDto> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findLowStockShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        when(inventoryClient.findLowStock()).thenReturn(List.of(response(UUID.randomUUID(), 2)));

        List<InventoryResponseDto> result = service.findLowStock();

        assertEquals(1, result.size());
    }

    @Test
    void findByProductShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        UUID productId = UUID.randomUUID();
        when(inventoryClient.findByProduct(productId)).thenReturn(response(productId, 8));

        InventoryResponseDto result = service.findByProduct(productId);

        assertEquals(productId, result.productId());
    }

    @Test
    void addStockShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        UUID productId = UUID.randomUUID();
        StockAdjustmentDto request = new StockAdjustmentDto(3);
        when(inventoryClient.addStock(productId, request)).thenReturn(response(productId, 13));

        InventoryResponseDto result = service.addStock(productId, request);

        assertEquals(13, result.stock());
    }

    @Test
    void subtractStockShouldDelegateToClient() {
        InventoryBffService service = new InventoryBffService(inventoryClient);
        UUID productId = UUID.randomUUID();
        StockAdjustmentDto request = new StockAdjustmentDto(3);
        when(inventoryClient.subtractStock(productId, request)).thenReturn(response(productId, 7));

        InventoryResponseDto result = service.subtractStock(productId, request);

        assertEquals(7, result.stock());
    }

    private InventoryResponseDto response(UUID productId, int stock) {
        return new InventoryResponseDto(UUID.randomUUID(), productId, stock, 5, "Principal", false,
                LocalDateTime.now());
    }
}
