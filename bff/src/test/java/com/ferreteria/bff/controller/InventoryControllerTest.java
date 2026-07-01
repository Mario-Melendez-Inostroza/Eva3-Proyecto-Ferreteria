package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.InventoryRequestDto;
import com.ferreteria.bff.dto.InventoryResponseDto;
import com.ferreteria.bff.dto.StockAdjustmentDto;
import com.ferreteria.bff.service.InventoryBffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryBffService inventoryBffService;

    @Test
    void createShouldReturnCreated() {
        InventoryController controller = new InventoryController(inventoryBffService);
        InventoryRequestDto request = new InventoryRequestDto(UUID.randomUUID(), 10, 5, "Principal");
        InventoryResponseDto response = response(request.productId(), 10, false);
        when(inventoryBffService.create(any())).thenReturn(response);

        ResponseEntity<InventoryResponseDto> result = controller.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(10, result.getBody().stock());
    }

    @Test
    void findAllShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryBffService);
        when(inventoryBffService.findAll()).thenReturn(List.of(response(UUID.randomUUID(), 8, false)));

        ResponseEntity<List<InventoryResponseDto>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findLowStockShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryBffService);
        when(inventoryBffService.findLowStock()).thenReturn(List.of(response(UUID.randomUUID(), 2, true)));

        ResponseEntity<List<InventoryResponseDto>> result = controller.findLowStock();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findByProductShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryBffService);
        UUID productId = UUID.randomUUID();
        when(inventoryBffService.findByProduct(productId)).thenReturn(response(productId, 8, false));

        ResponseEntity<InventoryResponseDto> result = controller.findByProduct(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(productId, result.getBody().productId());
    }

    @Test
    void addStockShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryBffService);
        UUID productId = UUID.randomUUID();
        when(inventoryBffService.addStock(any(), any())).thenReturn(response(productId, 13, false));

        ResponseEntity<InventoryResponseDto> result = controller.addStock(productId, new StockAdjustmentDto(3));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(13, result.getBody().stock());
    }

    @Test
    void subtractStockShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryBffService);
        UUID productId = UUID.randomUUID();
        when(inventoryBffService.subtractStock(any(), any())).thenReturn(response(productId, 7, false));

        ResponseEntity<InventoryResponseDto> result = controller.subtractStock(productId, new StockAdjustmentDto(3));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(7, result.getBody().stock());
    }

    private InventoryResponseDto response(UUID productId, int stock, boolean lowStock) {
        return new InventoryResponseDto(UUID.randomUUID(), productId, stock, 5, "Principal", lowStock,
                LocalDateTime.now());
    }
}
