package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record SaleRequestDto(
    @NotNull(message = "productId is required") UUID productId,
    @JsonProperty("cantidad")
    @NotNull(message = "Quantity is required") @Min(1) Integer quantity,
    @JsonProperty("cliente")
    String customer) {}
