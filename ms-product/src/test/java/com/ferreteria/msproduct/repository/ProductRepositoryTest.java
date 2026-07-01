package com.ferreteria.msproduct.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNombreContainingIgnoreCaseAndActivoTrue_shouldFindExisting() {
        var result = productRepository.findByNombreContainingIgnoreCaseAndActivoTrue("Taladro");
        assertTrue(!result.isEmpty());
    }
}
