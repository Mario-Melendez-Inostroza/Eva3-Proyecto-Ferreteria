package com.ferreteria.mssales.repository;

import com.ferreteria.mssales.model.Venta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Test
    void shouldFindSalesByProductId() {
        Venta venta = Venta.builder()
                .productId(UUID.randomUUID())
                .cantidad(2)
                .precioUnit(new BigDecimal("89990"))
                .total(new BigDecimal("179980"))
                .cliente("Juan")
                .createdAt(LocalDateTime.now())
                .build();

        ventaRepository.save(venta);

        List<Venta> result = ventaRepository.findByProductIdOrderByCreatedAtDesc(venta.getProductId());

        assertEquals(1, result.size());
    }
}
