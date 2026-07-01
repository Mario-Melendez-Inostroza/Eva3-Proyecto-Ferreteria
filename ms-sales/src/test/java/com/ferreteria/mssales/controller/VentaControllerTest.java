package com.ferreteria.mssales.controller;

import com.ferreteria.mssales.dto.ResumenVentasDto;
import com.ferreteria.mssales.dto.VentaRequestDto;
import com.ferreteria.mssales.dto.VentaResponseDto;
import com.ferreteria.mssales.service.VentaService;
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
class VentaControllerTest {

    @Mock
    private VentaService ventaService;

    @Test
    void registrarShouldReturnCreated() {
        VentaController controller = new VentaController(ventaService);
        VentaRequestDto request = new VentaRequestDto(UUID.randomUUID(), 2, "Juan");
        VentaResponseDto response = new VentaResponseDto(UUID.randomUUID(), request.productId(), "Taladro", 2,
                new BigDecimal("89990"), new BigDecimal("179980"), "Juan", LocalDateTime.now());
        when(ventaService.registrarVenta(any())).thenReturn(response);

        ResponseEntity<VentaResponseDto> result = controller.registrar(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Taladro", result.getBody().nombreProducto());
    }

    @Test
    void listarShouldReturnOk() {
        VentaController controller = new VentaController(ventaService);
        VentaResponseDto response = new VentaResponseDto(UUID.randomUUID(), UUID.randomUUID(), "Martillo", 1,
                new BigDecimal("12990"), new BigDecimal("12990"), "Ana", LocalDateTime.now());
        when(ventaService.listarTodas()).thenReturn(List.of(response));

        ResponseEntity<List<VentaResponseDto>> result = controller.listar();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void buscarPorIdShouldReturnOk() {
        VentaController controller = new VentaController(ventaService);
        UUID id = UUID.randomUUID();
        VentaResponseDto response = new VentaResponseDto(id, UUID.randomUUID(), "Martillo", 1,
                new BigDecimal("12990"), new BigDecimal("12990"), "Ana", LocalDateTime.now());
        when(ventaService.buscarPorId(id)).thenReturn(response);

        ResponseEntity<VentaResponseDto> result = controller.buscarPorId(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(id, result.getBody().id());
    }

    @Test
    void listarPorProductoShouldReturnOk() {
        VentaController controller = new VentaController(ventaService);
        UUID productId = UUID.randomUUID();
        VentaResponseDto response = new VentaResponseDto(UUID.randomUUID(), productId, "Martillo", 1,
                new BigDecimal("12990"), new BigDecimal("12990"), "Ana", LocalDateTime.now());
        when(ventaService.listarPorProducto(productId)).thenReturn(List.of(response));

        ResponseEntity<List<VentaResponseDto>> result = controller.listarPorProducto(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(productId, result.getBody().get(0).productId());
    }

    @Test
    void resumenShouldReturnOk() {
        VentaController controller = new VentaController(ventaService);
        ResumenVentasDto response = new ResumenVentasDto(1L, new BigDecimal("12990"));
        when(ventaService.obtenerResumen()).thenReturn(response);

        ResponseEntity<ResumenVentasDto> result = controller.resumen();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().totalVentas());
    }
}
