package com.ferreteria.msinventory.repository;

import com.ferreteria.msinventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByProductId(UUID productId);
    boolean existsByProductId(UUID productId);

    // Productos con stock menor o igual al stock mínimo configurado
    @Query("SELECT i FROM Inventory i WHERE i.stock <= i.stockMinimo")
    List<Inventory> findStockBajo();
}
