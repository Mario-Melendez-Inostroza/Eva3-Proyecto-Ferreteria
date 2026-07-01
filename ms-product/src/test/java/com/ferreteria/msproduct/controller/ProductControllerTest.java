package com.ferreteria.msproduct.controller;

import com.ferreteria.msproduct.dto.MessageResponseDto;
import com.ferreteria.msproduct.dto.ProductRequestDto;
import com.ferreteria.msproduct.dto.ProductResponseDto;
import com.ferreteria.msproduct.service.ProductService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Test
    void listar_shouldReturnOkWithList() {
        ProductController controller = new ProductController(productService);
        ProductResponseDto p = response(UUID.randomUUID(), "Taladro", "Taladro profesional", new BigDecimal("89990"),
                "Herramientas", true);
        when(productService.listarActivos()).thenReturn(List.of(p));

        ResponseEntity<List<ProductResponseDto>> result = controller.listar();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Taladro", result.getBody().get(0).nombre());
    }

    @Test
    void buscarPorId_shouldReturnOk() {
        ProductController controller = new ProductController(productService);
        UUID id = UUID.randomUUID();
        ProductResponseDto p = response(id, "Martillo", "Martillo carpintero", new BigDecimal("8990"), "Herramientas",
                true);
        when(productService.buscarPorId(any())).thenReturn(p);

        ResponseEntity<ProductResponseDto> result = controller.buscarPorId(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Martillo", result.getBody().nombre());
    }

    @Test
    void buscarPorNombre_shouldReturnOkWithList() {
        ProductController controller = new ProductController(productService);
        ProductResponseDto p = response(UUID.randomUUID(), "Taladro", "Taladro profesional", new BigDecimal("89990"),
                "Herramientas", true);
        when(productService.buscarPorNombre("tal")).thenReturn(List.of(p));

        ResponseEntity<List<ProductResponseDto>> result = controller.buscarPorNombre("tal");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Taladro", result.getBody().get(0).nombre());
    }

    @Test
    void buscarPorCategoria_shouldReturnOkWithList() {
        ProductController controller = new ProductController(productService);
        ProductResponseDto p = response(UUID.randomUUID(), "Martillo", "Martillo carpintero", new BigDecimal("8990"),
                "Herramientas", true);
        when(productService.buscarPorCategoria("Herramientas")).thenReturn(List.of(p));

        ResponseEntity<List<ProductResponseDto>> result = controller.buscarPorCategoria("Herramientas");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Martillo", result.getBody().get(0).nombre());
    }

    @Test
    void crear_shouldReturnCreated() {
        ProductController controller = new ProductController(productService);
        ProductRequestDto req = new ProductRequestDto("Llave", "Llave ajustable", new BigDecimal("12990"),
                "Ferreteria");
        ProductResponseDto resp = response(UUID.randomUUID(), "Llave", "Llave ajustable", new BigDecimal("12990"),
                "Ferreteria", true);
        when(productService.crear(any())).thenReturn(resp);

        ResponseEntity<ProductResponseDto> result = controller.crear(req);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Llave", result.getBody().nombre());
    }

    @Test
    void actualizar_shouldReturnOk() {
        ProductController controller = new ProductController(productService);
        UUID id = UUID.randomUUID();
        ProductRequestDto req = new ProductRequestDto("Cinta", "Cinta metrica", new BigDecimal("4990"), "Medicion");
        ProductResponseDto resp = response(id, "Cinta", "Cinta metrica", new BigDecimal("4990"), "Medicion", true);
        when(productService.actualizar(any(), any())).thenReturn(resp);

        ResponseEntity<ProductResponseDto> result = controller.actualizar(id, req);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Cinta", result.getBody().nombre());
    }

    @Test
    void desactivar_shouldReturnMessage() {
        ProductController controller = new ProductController(productService);
        UUID id = UUID.randomUUID();
        MessageResponseDto msg = new MessageResponseDto("Producto desactivado: Taladro");
        when(productService.desactivar(any())).thenReturn(msg);

        ResponseEntity<MessageResponseDto> result = controller.desactivar(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().message().contains("desactivado"));
    }

    private ProductResponseDto response(UUID id, String nombre, String descripcion, BigDecimal precio, String categoria,
            boolean activo) {
        return new ProductResponseDto(id, nombre, descripcion, precio, categoria, activo, LocalDateTime.now());
    }
}
