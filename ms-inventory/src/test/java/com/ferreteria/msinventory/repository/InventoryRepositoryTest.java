package com.ferreteria.msinventory.repository;

import com.ferreteria.msinventory.model.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void shouldFindInventoryByProductId() {
        Inventory inventory = Inventory.builder()
                .productId(UUID.randomUUID())
                .stock(7)
                .stockMinimo(5)
                .bodega("Principal")
                .updatedAt(LocalDateTime.now())
                .build();

        inventoryRepository.save(inventory);

        Optional<Inventory> found = inventoryRepository.findByProductId(inventory.getProductId());

        assertTrue(found.isPresent());
        assertEquals(7, found.get().getStock());
    }

    @Test
    void shouldListLowStockInventory() {
        Inventory inventory = Inventory.builder()
                .productId(UUID.randomUUID())
                .stock(2)
                .stockMinimo(3)
                .bodega("Principal")
                .updatedAt(LocalDateTime.now())
                .build();

        inventoryRepository.save(inventory);

        List<Inventory> result = inventoryRepository.findStockBajo();

        assertEquals(1, result.size());
    }
}
