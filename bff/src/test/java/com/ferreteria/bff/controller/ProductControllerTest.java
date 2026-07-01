package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.ProductRequestDto;
import com.ferreteria.bff.dto.ProductResponseDto;
import com.ferreteria.bff.service.ProductBffService;
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
class ProductControllerTest {

    @Mock
    private ProductBffService productBffService;

    @Test
    void findAllShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        ProductResponseDto response = new ProductResponseDto(UUID.randomUUID(), "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.findAll()).thenReturn(List.of(response));

        ResponseEntity<List<ProductResponseDto>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void findByIdShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        UUID id = UUID.randomUUID();
        ProductResponseDto response = new ProductResponseDto(id, "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.findById(id)).thenReturn(response);

        ResponseEntity<ProductResponseDto> result = controller.findById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(id, result.getBody().id());
    }

    @Test
    void findByNameShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        ProductResponseDto response = new ProductResponseDto(UUID.randomUUID(), "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.findByName("tal")).thenReturn(List.of(response));

        ResponseEntity<List<ProductResponseDto>> result = controller.findByName("tal");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findByCategoryShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        ProductResponseDto response = new ProductResponseDto(UUID.randomUUID(), "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.findByCategory("Herramientas")).thenReturn(List.of(response));

        ResponseEntity<List<ProductResponseDto>> result = controller.findByCategory("Herramientas");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void createShouldReturnCreated() {
        ProductController controller = new ProductController(productBffService);
        ProductRequestDto request = new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas");
        ProductResponseDto response = new ProductResponseDto(UUID.randomUUID(), "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.create(any())).thenReturn(response);

        ResponseEntity<ProductResponseDto> result = controller.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void updateShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        UUID id = UUID.randomUUID();
        ProductRequestDto request = new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas");
        ProductResponseDto response = new ProductResponseDto(id, "Taladro", "desc",
                new BigDecimal("89990"), "Herramientas", true, LocalDateTime.now());
        when(productBffService.update(any(), any())).thenReturn(response);

        ResponseEntity<ProductResponseDto> result = controller.update(id, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(id, result.getBody().id());
    }

    @Test
    void deactivateShouldReturnOk() {
        ProductController controller = new ProductController(productBffService);
        MessageResponseDto response = new MessageResponseDto("Producto desactivado");
        when(productBffService.deactivate(any())).thenReturn(response);

        ResponseEntity<MessageResponseDto> result = controller.deactivate(UUID.randomUUID());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }
}
