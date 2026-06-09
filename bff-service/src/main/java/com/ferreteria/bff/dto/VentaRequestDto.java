package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record VentaRequestDto(
    @NotNull(message = "productId obligatorio") UUID productId,
    @NotNull(message = "Cantidad obligatoria") @Min(1) Integer cantidad,
    String cliente) {}
