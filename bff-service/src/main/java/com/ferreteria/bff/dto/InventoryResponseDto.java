package com.ferreteria.bff.dto;
import java.time.LocalDateTime;
import java.util.UUID;
public record InventoryResponseDto(
    UUID id, UUID productId, Integer stock,
    Integer stockMinimo, String bodega,
    boolean stockBajo, LocalDateTime updatedAt) {}
