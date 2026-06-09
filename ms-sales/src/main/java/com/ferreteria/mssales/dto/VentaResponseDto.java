package com.ferreteria.mssales.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VentaResponseDto(
    UUID id,
    UUID productId,
    String nombreProducto,
    Integer cantidad,
    BigDecimal precioUnit,
    BigDecimal total,
    String cliente,
    LocalDateTime createdAt
) {}
