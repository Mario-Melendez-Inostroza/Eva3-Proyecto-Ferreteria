package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.SaleRequestDto;
import com.ferreteria.bff.dto.SaleResponseDto;
import com.ferreteria.bff.dto.SalesSummaryDto;
import com.ferreteria.bff.service.SalesBffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesControllerTest {

    @Mock
    private SalesBffService salesBffService;

    @Test
    void registerShouldReturnCreated() {
        SalesController controller = new SalesController(salesBffService);
        SaleRequestDto request = new SaleRequestDto(UUID.randomUUID(), 2, "Juan");
        SaleResponseDto response = response(request.productId());
        when(salesBffService.register(any())).thenReturn(response);

        ResponseEntity<SaleResponseDto> result = controller.register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Taladro", result.getBody().productName());
    }

    @Test
    void findAllShouldReturnOk() {
        SalesController controller = new SalesController(salesBffService);
        when(salesBffService.findAll()).thenReturn(List.of(response(UUID.randomUUID())));

        ResponseEntity<List<SaleResponseDto>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findByIdShouldReturnOk() {
        SalesController controller = new SalesController(salesBffService);
        UUID saleId = UUID.randomUUID();
        SaleResponseDto response = response(UUID.randomUUID());
        when(salesBffService.findById(saleId)).thenReturn(response);

        ResponseEntity<SaleResponseDto> result = controller.findById(saleId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void findByProductShouldReturnOk() {
        SalesController controller = new SalesController(salesBffService);
        UUID productId = UUID.randomUUID();
        when(salesBffService.findByProduct(productId)).thenReturn(List.of(response(productId)));

        ResponseEntity<List<SaleResponseDto>> result = controller.findByProduct(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(productId, result.getBody().get(0).productId());
    }

    @Test
    void summaryShouldReturnOk() {
        SalesController controller = new SalesController(salesBffService);
        when(salesBffService.summary()).thenReturn(new SalesSummaryDto(2L, new BigDecimal("179980")));

        ResponseEntity<SalesSummaryDto> result = controller.summary();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2L, result.getBody().totalSales());
    }

    private SaleResponseDto response(UUID productId) {
        return new SaleResponseDto(UUID.randomUUID(), productId, "Taladro", 2, new BigDecimal("89990"),
                new BigDecimal("179980"), "Juan", LocalDateTime.now());
    }
}
