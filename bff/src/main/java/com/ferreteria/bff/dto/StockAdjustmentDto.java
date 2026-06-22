package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
public record StockAdjustmentDto(
    @JsonProperty("cantidad")
    @NotNull(message = "Quantity is required") @Min(1) Integer quantity) {}
