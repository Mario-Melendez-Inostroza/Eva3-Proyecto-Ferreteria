package com.ferreteria.bff.service;

import com.ferreteria.bff.client.ProductClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.ProductRequestDto;
import com.ferreteria.bff.dto.ProductResponseDto;
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
class ProductBffServiceTest {

    @Mock
    private ProductClient productClient;

    @Test
    void findAllShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        when(productClient.findAll()).thenReturn(List.of(response(UUID.randomUUID())));

        List<ProductResponseDto> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findByIdShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        UUID id = UUID.randomUUID();
        when(productClient.findById(id)).thenReturn(response(id));

        ProductResponseDto result = service.findById(id);

        assertEquals(id, result.id());
    }

    @Test
    void findByNameShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        when(productClient.findByName("tal")).thenReturn(List.of(response(UUID.randomUUID())));

        List<ProductResponseDto> result = service.findByName("tal");

        assertEquals(1, result.size());
    }

    @Test
    void findByCategoryShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        when(productClient.findByCategory("Herramientas")).thenReturn(List.of(response(UUID.randomUUID())));

        List<ProductResponseDto> result = service.findByCategory("Herramientas");

        assertEquals(1, result.size());
    }

    @Test
    void createShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        ProductRequestDto request = request();
        when(productClient.create(request)).thenReturn(response(UUID.randomUUID()));

        ProductResponseDto result = service.create(request);

        assertEquals("Taladro", result.name());
    }

    @Test
    void updateShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        UUID id = UUID.randomUUID();
        ProductRequestDto request = request();
        when(productClient.update(id, request)).thenReturn(response(id));

        ProductResponseDto result = service.update(id, request);

        assertEquals(id, result.id());
    }

    @Test
    void deactivateShouldDelegateToClient() {
        ProductBffService service = new ProductBffService(productClient);
        UUID id = UUID.randomUUID();
        when(productClient.deactivate(id)).thenReturn(new MessageResponseDto("ok"));

        MessageResponseDto result = service.deactivate(id);

        assertEquals("ok", result.message());
    }

    private ProductRequestDto request() {
        return new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas");
    }

    private ProductResponseDto response(UUID id) {
        return new ProductResponseDto(id, "Taladro", "desc", new BigDecimal("89990"), "Herramientas", true,
                LocalDateTime.now());
    }
}
