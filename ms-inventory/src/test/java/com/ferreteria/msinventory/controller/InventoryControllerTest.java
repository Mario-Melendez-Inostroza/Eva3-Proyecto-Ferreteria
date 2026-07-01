package com.ferreteria.msinventory.controller;

import com.ferreteria.msinventory.dto.AjustarStockDto;
import com.ferreteria.msinventory.dto.InventoryRequestDto;
import com.ferreteria.msinventory.dto.InventoryResponseDto;
import com.ferreteria.msinventory.service.InventoryService;
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
    private InventoryService inventoryService;

    @Test
    void crearShouldReturnCreated() {
        InventoryController controller = new InventoryController(inventoryService);
        InventoryRequestDto request = new InventoryRequestDto(UUID.randomUUID(), 10, 5, "Principal");
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), request.productId(), 10, 5,
                "Principal", false, LocalDateTime.now());
        when(inventoryService.crear(any())).thenReturn(response);

        ResponseEntity<InventoryResponseDto> result = controller.crear(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(10, result.getBody().stock());
    }

    @Test
    void listarShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryService);
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), UUID.randomUUID(), 8, 3,
                "Principal", false, LocalDateTime.now());
        when(inventoryService.listarTodo()).thenReturn(List.of(response));

        ResponseEntity<List<InventoryResponseDto>> result = controller.listar();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void stockBajoShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryService);
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), UUID.randomUUID(), 2, 5,
                "Principal", true, LocalDateTime.now());
        when(inventoryService.listarStockBajo()).thenReturn(List.of(response));

        ResponseEntity<List<InventoryResponseDto>> result = controller.stockBajo();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(2, result.getBody().get(0).stock());
    }

    @Test
    void buscarPorProductoShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryService);
        UUID productId = UUID.randomUUID();
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), productId, 8, 3,
                "Principal", false, LocalDateTime.now());
        when(inventoryService.buscarPorProductId(productId)).thenReturn(response);

        ResponseEntity<InventoryResponseDto> result = controller.buscarPorProducto(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(productId, result.getBody().productId());
    }

    @Test
    void agregarStockShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryService);
        UUID productId = UUID.randomUUID();
        AjustarStockDto request = new AjustarStockDto(3);
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), productId, 13, 5, "Principal",
                false, LocalDateTime.now());
        when(inventoryService.agregarStock(any(), any())).thenReturn(response);

        ResponseEntity<InventoryResponseDto> result = controller.agregarStock(productId, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(13, result.getBody().stock());
    }

    @Test
    void descontarStockShouldReturnOk() {
        InventoryController controller = new InventoryController(inventoryService);
        UUID productId = UUID.randomUUID();
        AjustarStockDto request = new AjustarStockDto(3);
        InventoryResponseDto response = new InventoryResponseDto(UUID.randomUUID(), productId, 7, 5, "Principal",
                false, LocalDateTime.now());
        when(inventoryService.descontarStock(any(), any())).thenReturn(response);

        ResponseEntity<InventoryResponseDto> result = controller.descontarStock(productId, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(7, result.getBody().stock());
    }
}
