package com.ferreteria.mssales.repository;

import com.ferreteria.mssales.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface VentaRepository extends JpaRepository<Venta, UUID> {
    List<Venta> findByProductIdOrderByCreatedAtDesc(UUID productId);
    List<Venta> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v")
    BigDecimal sumTotalVentas();
}
