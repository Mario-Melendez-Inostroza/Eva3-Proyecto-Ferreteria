package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record InventoryRequestDto(
    @NotNull(message = "productId is required") UUID productId,
    @NotNull(message = "Stock is required") @Min(0) Integer stock,
    @JsonProperty("stockMinimo")
    Integer minimumStock,
    @JsonProperty("bodega")
    String warehouse) {}
