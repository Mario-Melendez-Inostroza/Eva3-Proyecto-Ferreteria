package com.ferreteria.msinventory.service;

import com.ferreteria.msinventory.dto.*;
import com.ferreteria.msinventory.exception.InsufficientStockException;
import com.ferreteria.msinventory.exception.InventoryNotFoundException;
import com.ferreteria.msinventory.model.Inventory;
import com.ferreteria.msinventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    // Convierte entidad a DTO
    private InventoryResponseDto toDto(Inventory i) {
        return new InventoryResponseDto(
                i.getId(), i.getProductId(), i.getStock(),
                i.getStockMinimo(), i.getBodega(),
                i.getStock() <= i.getStockMinimo(), // calcula si stock es bajo
                i.getUpdatedAt()
        );
    }

    @Override
    public InventoryResponseDto crear(InventoryRequestDto dto) {
        if (inventoryRepository.existsByProductId(dto.productId())) {
            throw new IllegalArgumentException("Ya existe inventario para productId: " + dto.productId());
        }
        Inventory inv = Inventory.builder()
                .productId(dto.productId())
                .stock(dto.stock() != null ? dto.stock() : 0)
                .stockMinimo(dto.stockMinimo() != null ? dto.stockMinimo() : 5)
                .bodega(dto.bodega() != null ? dto.bodega() : "Principal")
                .build();
        return toDto(inventoryRepository.save(inv));
    }

    @Override
    public InventoryResponseDto buscarPorProductId(UUID productId) {
        return toDto(inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventario no encontrado para producto: " + productId)));
    }

    @Override
    public List<InventoryResponseDto> listarTodo() {
        return inventoryRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<InventoryResponseDto> listarStockBajo() {
        return inventoryRepository.findStockBajo().stream().map(this::toDto).toList();
    }

    @Override
    public InventoryResponseDto agregarStock(UUID productId, AjustarStockDto dto) {
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventario no encontrado para producto: " + productId));
        inv.setStock(inv.getStock() + dto.cantidad());
        return toDto(inventoryRepository.save(inv));
    }

    @Override
    public InventoryResponseDto descontarStock(UUID productId, AjustarStockDto dto) {
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventario no encontrado para producto: " + productId));
        if (inv.getStock() < dto.cantidad()) {
            throw new InsufficientStockException(
                    "Stock insuficiente. Stock actual: " + inv.getStock()
                    + ", solicitado: " + dto.cantidad());
        }
        inv.setStock(inv.getStock() - dto.cantidad());
        return toDto(inventoryRepository.save(inv));
    }
}
