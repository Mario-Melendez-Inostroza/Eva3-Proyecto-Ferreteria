package com.ferreteria.mssales.service;

import com.ferreteria.mssales.client.*;
import com.ferreteria.mssales.dto.*;
import com.ferreteria.mssales.exception.BusinessException;
import com.ferreteria.mssales.exception.ResourceNotFoundException;
import com.ferreteria.mssales.model.Venta;
import com.ferreteria.mssales.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final NotificationClient notificationClient;

    // ==========================================================
    // registrarVenta() — FLUJO PRINCIPAL DE UNA VENTA
    // ==========================================================
    // 1. Verifica que el producto existe en ms-product
    // 2. Descuenta el stock en ms-inventory
    //    (ms-inventory lanza excepción si no hay suficiente)
    // 3. Guarda la venta en la BD
    // 4. Si el stock quedó bajo → notifica a ms-notification
    // 5. Notifica la venta registrada a ms-notification
    // 6. Retorna el detalle de la venta
    // ==========================================================
    @Override
    public VentaResponseDto registrarVenta(VentaRequestDto dto) {

        // PASO 1: Verificar que el producto existe y está activo
        ProductDto producto;
        try {
            producto = productClient.buscarProducto(dto.productId());
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    "Producto no encontrado: " + dto.productId());
        }

        if (producto == null || !Boolean.TRUE.equals(producto.activo())) {
            throw new BusinessException("El producto no está disponible para venta");
        }

        // PASO 2: Descontar stock en ms-inventory
        InventoryDto inventarioActualizado;
        try {
            inventarioActualizado = inventoryClient.descontarStock(dto.productId(), dto.cantidad());
        } catch (Exception e) {
            throw new BusinessException(
                    "No se pudo descontar el stock: " + e.getMessage());
        }

        // PASO 3: Guardar la venta
        Venta venta = ventaRepository.save(Venta.builder()
                .productId(dto.productId())
                .cantidad(dto.cantidad())
                .precioUnit(producto.precio())
                .total(producto.precio().multiply(java.math.BigDecimal.valueOf(dto.cantidad())))
                .cliente(dto.cliente())
                .build());

        // PASO 4: Notificar si el stock quedó bajo (de forma segura, sin romper la venta)
        try {
            if (inventarioActualizado.stockBajo()) {
                notificationClient.notificarStockBajo(
                        dto.productId(),
                        producto.nombre(),
                        inventarioActualizado.stock()
                );
            }
            // PASO 5: Notificar venta registrada
            notificationClient.notificarVenta(dto.productId(), producto.nombre(), dto.cantidad());
        } catch (Exception e) {
            // Si falla la notificación, la venta igual se registró
            // Solo logueamos el error sin revertir
            log.warn("No se pudo enviar notificación: {}", e.getMessage());
        }

        return new VentaResponseDto(
                venta.getId(), venta.getProductId(), producto.nombre(),
                venta.getCantidad(), venta.getPrecioUnit(),
                venta.getTotal(), venta.getCliente(), venta.getCreatedAt()
        );
    }

    @Override
    public List<VentaResponseDto> listarTodas() {
        return ventaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(v -> new VentaResponseDto(
                        v.getId(), v.getProductId(), null,
                        v.getCantidad(), v.getPrecioUnit(),
                        v.getTotal(), v.getCliente(), v.getCreatedAt()))
                .toList();
    }

    @Override
    public List<VentaResponseDto> listarPorProducto(UUID productId) {
        return ventaRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(v -> new VentaResponseDto(
                        v.getId(), v.getProductId(), null,
                        v.getCantidad(), v.getPrecioUnit(),
                        v.getTotal(), v.getCliente(), v.getCreatedAt()))
                .toList();
    }

    @Override
    public VentaResponseDto buscarPorId(UUID id) {
        Venta v = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada: " + id));
        return new VentaResponseDto(
                v.getId(), v.getProductId(), null,
                v.getCantidad(), v.getPrecioUnit(),
                v.getTotal(), v.getCliente(), v.getCreatedAt());
    }

    @Override
    public ResumenVentasDto obtenerResumen() {
        return new ResumenVentasDto(
                ventaRepository.count(),
                ventaRepository.sumTotalVentas()
        );
    }
}
