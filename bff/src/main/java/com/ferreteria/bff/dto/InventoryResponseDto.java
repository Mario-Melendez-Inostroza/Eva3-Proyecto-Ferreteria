package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;
public record InventoryResponseDto(
    UUID id, UUID productId, Integer stock,
    @JsonProperty("stockMinimo")
    Integer minimumStock,
    @JsonProperty("bodega")
    String warehouse,
    @JsonProperty("stockBajo")
    boolean lowStock,
    LocalDateTime updatedAt) {}
