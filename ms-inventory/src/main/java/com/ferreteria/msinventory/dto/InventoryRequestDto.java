package com.ferreteria.msinventory.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryRequestDto(
    @NotNull(message = "productId es obligatorio") UUID productId,
    @NotNull(message = "Stock es obligatorio")
    @Min(value = 0, message = "Stock no puede ser negativo") Integer stock,
    @Min(value = 0, message = "Stock mínimo no puede ser negativo") Integer stockMinimo,
    String bodega
) {}
