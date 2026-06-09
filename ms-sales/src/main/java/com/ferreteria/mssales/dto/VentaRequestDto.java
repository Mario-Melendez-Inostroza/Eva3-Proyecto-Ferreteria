package com.ferreteria.mssales.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VentaRequestDto(
    @NotNull(message = "productId es obligatorio") UUID productId,
    @NotNull(message = "Cantidad es obligatoria")
    @Min(value = 1, message = "Cantidad debe ser mayor a 0") Integer cantidad,
    String cliente
) {}
