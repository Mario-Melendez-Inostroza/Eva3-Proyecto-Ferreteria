package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record InventoryRequestDto(
    @NotNull(message = "productId obligatorio") UUID productId,
    @NotNull(message = "Stock obligatorio") @Min(0) Integer stock,
    Integer stockMinimo,
    String bodega) {}
