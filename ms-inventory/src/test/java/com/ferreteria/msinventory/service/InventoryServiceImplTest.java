package com.ferreteria.msinventory.service;

import com.ferreteria.msinventory.dto.AjustarStockDto;
import com.ferreteria.msinventory.dto.InventoryRequestDto;
import com.ferreteria.msinventory.dto.InventoryResponseDto;
import com.ferreteria.msinventory.exception.InsufficientStockException;
import com.ferreteria.msinventory.exception.InventoryNotFoundException;
import com.ferreteria.msinventory.model.Inventory;
import com.ferreteria.msinventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    void crearShouldSaveInventory() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        when(inventoryRepository.existsByProductId(productId)).thenReturn(false);
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponseDto result = service.crear(new InventoryRequestDto(productId, 10, 5, "Principal"));

        assertNotNull(result);
        assertEquals(10, result.stock());
        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void buscarPorProductIdShouldThrowWhenNotFound() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        InventoryNotFoundException ex = assertThrows(InventoryNotFoundException.class,
                () -> service.buscarPorProductId(productId));

        assertNotNull(ex.getMessage());
    }

    @Test
    void buscarPorProductIdShouldReturnWhenExists() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        Inventory inventory = Inventory.builder().productId(productId).stock(7).stockMinimo(3).bodega("Principal")
                .updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));

        InventoryResponseDto result = service.buscarPorProductId(productId);

        assertEquals(productId, result.productId());
        assertEquals(7, result.stock());
    }

    @Test
    void listarTodoShouldReturnAllInventory() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        Inventory inventory = Inventory.builder().productId(UUID.randomUUID()).stock(8).stockMinimo(3)
                .bodega("Principal").updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));

        List<InventoryResponseDto> result = service.listarTodo();

        assertEquals(1, result.size());
        assertEquals(8, result.get(0).stock());
    }

    @Test
    void agregarStockShouldIncreaseQuantity() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        Inventory inventory = Inventory.builder().productId(productId).stock(5).stockMinimo(3).bodega("Principal")
                .updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponseDto result = service.agregarStock(productId, new AjustarStockDto(4));

        assertEquals(9, result.stock());
    }

    @Test
    void listarStockBajoShouldReturnLowStockItems() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        Inventory inventory = Inventory.builder().productId(UUID.randomUUID()).stock(2).stockMinimo(3)
                .bodega("Principal").updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findStockBajo()).thenReturn(List.of(inventory));

        List<InventoryResponseDto> result = service.listarStockBajo();

        assertEquals(1, result.size());
    }

    @Test
    void descontarStockShouldDecreaseQuantity() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        Inventory inventory = Inventory.builder().productId(productId).stock(10).stockMinimo(3).bodega("Principal")
                .updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponseDto result = service.descontarStock(productId, new AjustarStockDto(4));

        assertEquals(6, result.stock());
    }

    @Test
    void descontarStockShouldThrowWhenStockIsInsufficient() {
        InventoryServiceImpl service = new InventoryServiceImpl(inventoryRepository);
        UUID productId = UUID.randomUUID();
        Inventory inventory = Inventory.builder().productId(productId).stock(2).stockMinimo(3).bodega("Principal")
                .updatedAt(LocalDateTime.now()).build();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));

        InsufficientStockException ex = assertThrows(InsufficientStockException.class,
                () -> service.descontarStock(productId, new AjustarStockDto(4)));

        assertNotNull(ex.getMessage());
        verify(inventoryRepository, never()).save(any());
    }
}
