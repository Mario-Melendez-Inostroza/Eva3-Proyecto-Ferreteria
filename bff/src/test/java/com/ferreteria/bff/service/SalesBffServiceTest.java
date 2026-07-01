package com.ferreteria.bff.service;

import com.ferreteria.bff.client.SalesClient;
import com.ferreteria.bff.dto.SaleRequestDto;
import com.ferreteria.bff.dto.SaleResponseDto;
import com.ferreteria.bff.dto.SalesSummaryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesBffServiceTest {

    @Mock
    private SalesClient salesClient;

    @Test
    void registerShouldDelegateToClient() {
        SalesBffService service = new SalesBffService(salesClient);
        SaleRequestDto request = new SaleRequestDto(UUID.randomUUID(), 2, "Juan");
        when(salesClient.register(request)).thenReturn(response(request.productId()));

        SaleResponseDto result = service.register(request);

        assertEquals("Taladro", result.productName());
    }

    @Test
    void findAllShouldDelegateToClient() {
        SalesBffService service = new SalesBffService(salesClient);
        when(salesClient.findAll()).thenReturn(List.of(response(UUID.randomUUID())));

        List<SaleResponseDto> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findByIdShouldDelegateToClient() {
        SalesBffService service = new SalesBffService(salesClient);
        UUID saleId = UUID.randomUUID();
        when(salesClient.findById(saleId)).thenReturn(response(UUID.randomUUID()));

        SaleResponseDto result = service.findById(saleId);

        assertEquals("Taladro", result.productName());
    }

    @Test
    void findByProductShouldDelegateToClient() {
        SalesBffService service = new SalesBffService(salesClient);
        UUID productId = UUID.randomUUID();
        when(salesClient.findByProduct(productId)).thenReturn(List.of(response(productId)));

        List<SaleResponseDto> result = service.findByProduct(productId);

        assertEquals(productId, result.get(0).productId());
    }

    @Test
    void summaryShouldDelegateToClient() {
        SalesBffService service = new SalesBffService(salesClient);
        when(salesClient.summary()).thenReturn(new SalesSummaryDto(2L, new BigDecimal("179980")));

        SalesSummaryDto result = service.summary();

        assertEquals(2L, result.totalSales());
    }

    private SaleResponseDto response(UUID productId) {
        return new SaleResponseDto(UUID.randomUUID(), productId, "Taladro", 2, new BigDecimal("89990"),
                new BigDecimal("179980"), "Juan", LocalDateTime.now());
    }
}
