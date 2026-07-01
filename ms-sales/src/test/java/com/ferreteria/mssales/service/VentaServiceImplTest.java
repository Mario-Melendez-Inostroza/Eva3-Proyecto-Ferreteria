package com.ferreteria.mssales.service;

import com.ferreteria.mssales.client.InventoryClient;
import com.ferreteria.mssales.client.NotificationClient;
import com.ferreteria.mssales.client.ProductClient;
import com.ferreteria.mssales.dto.InventoryDto;
import com.ferreteria.mssales.dto.ProductDto;
import com.ferreteria.mssales.dto.ResumenVentasDto;
import com.ferreteria.mssales.dto.VentaRequestDto;
import com.ferreteria.mssales.dto.VentaResponseDto;
import com.ferreteria.mssales.exception.BusinessException;
import com.ferreteria.mssales.exception.ResourceNotFoundException;
import com.ferreteria.mssales.exception.SaleNotFoundException;
import com.ferreteria.mssales.model.Venta;
import com.ferreteria.mssales.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductClient productClient;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private NotificationClient notificationClient;

    @Test
    void registrarVentaShouldThrowWhenProductIsMissing() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        VentaRequestDto request = new VentaRequestDto(UUID.randomUUID(), 2, "Juan");
        when(productClient.buscarProducto(request.productId())).thenThrow(new RuntimeException("not found"));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.registrarVenta(request));

        assertNotNull(ex.getMessage());
    }

    @Test
    void registrarVentaShouldReturnResponseWhenFlowSucceeds() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID productId = UUID.randomUUID();
        VentaRequestDto request = new VentaRequestDto(productId, 2, "Juan");
        ProductDto product = new ProductDto(productId, "Taladro", new BigDecimal("89990"), true);
        InventoryDto inventory = new InventoryDto(UUID.randomUUID(), productId, 10, 5, false);
        Venta venta = Venta.builder().id(UUID.randomUUID()).productId(productId).cantidad(2)
                .precioUnit(new BigDecimal("89990")).total(new BigDecimal("179980")).cliente("Juan")
                .createdAt(LocalDateTime.now()).build();
        when(productClient.buscarProducto(productId)).thenReturn(product);
        when(inventoryClient.descontarStock(productId, 2)).thenReturn(inventory);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        VentaResponseDto result = service.registrarVenta(request);

        assertNotNull(result);
        assertEquals("Taladro", result.nombreProducto());
    }

    @Test
    void registrarVentaShouldThrowWhenProductIsUnavailable() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID productId = UUID.randomUUID();
        VentaRequestDto request = new VentaRequestDto(productId, 1, "Juan");
        ProductDto product = new ProductDto(productId, "Taladro", new BigDecimal("89990"), false);
        when(productClient.buscarProducto(productId)).thenReturn(product);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.registrarVenta(request));

        assertNotNull(ex.getMessage());
    }

    @Test
    void listarTodasShouldReturnSalesWithProductName() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID productId = UUID.randomUUID();
        Venta venta = venta(productId);
        when(ventaRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(venta));
        when(productClient.buscarProducto(productId))
                .thenReturn(new ProductDto(productId, "Taladro", new BigDecimal("89990"), true));

        List<VentaResponseDto> result = service.listarTodas();

        assertEquals(1, result.size());
        assertEquals("Taladro", result.get(0).nombreProducto());
    }

    @Test
    void listarPorProductoShouldReturnSalesWithProductName() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID productId = UUID.randomUUID();
        Venta venta = venta(productId);
        when(ventaRepository.findByProductIdOrderByCreatedAtDesc(productId)).thenReturn(List.of(venta));
        when(productClient.buscarProducto(productId))
                .thenReturn(new ProductDto(productId, "Taladro", new BigDecimal("89990"), true));

        List<VentaResponseDto> result = service.listarPorProducto(productId);

        assertEquals(1, result.size());
        assertEquals(productId, result.get(0).productId());
    }

    @Test
    void buscarPorIdShouldReturnWhenExists() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID productId = UUID.randomUUID();
        Venta venta = venta(productId);
        UUID saleId = venta.getId();
        when(ventaRepository.findById(saleId)).thenReturn(Optional.of(venta));
        when(productClient.buscarProducto(productId))
                .thenReturn(new ProductDto(productId, "Taladro", new BigDecimal("89990"), true));

        VentaResponseDto result = service.buscarPorId(saleId);

        assertEquals(saleId, result.id());
        assertEquals("Taladro", result.nombreProducto());
    }

    @Test
    void buscarPorIdShouldThrowWhenMissing() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        UUID saleId = UUID.randomUUID();
        when(ventaRepository.findById(saleId)).thenReturn(Optional.empty());

        SaleNotFoundException ex = assertThrows(SaleNotFoundException.class, () -> service.buscarPorId(saleId));

        assertNotNull(ex.getMessage());
    }

    @Test
    void obtenerResumenShouldReturnTotals() {
        VentaServiceImpl service = new VentaServiceImpl(ventaRepository, productClient, inventoryClient,
                notificationClient);
        when(ventaRepository.count()).thenReturn(2L);
        when(ventaRepository.sumTotalVentas()).thenReturn(new BigDecimal("179980"));

        ResumenVentasDto result = service.obtenerResumen();

        assertEquals(2L, result.totalVentas());
        assertEquals(new BigDecimal("179980"), result.montoTotal());
    }

    private Venta venta(UUID productId) {
        return Venta.builder().id(UUID.randomUUID()).productId(productId).cantidad(2)
                .precioUnit(new BigDecimal("89990")).total(new BigDecimal("179980")).cliente("Juan")
                .createdAt(LocalDateTime.now()).build();
    }
}
