package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
public record AjustarStockDto(
    @NotNull(message = "Cantidad obligatoria") @Min(1) Integer cantidad) {}
