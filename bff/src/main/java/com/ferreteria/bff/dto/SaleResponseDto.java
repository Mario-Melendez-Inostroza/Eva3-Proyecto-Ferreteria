package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
public record SaleResponseDto(
    UUID id, UUID productId,
    @JsonProperty("nombreProducto")
    String productName,
    @JsonProperty("cantidad")
    Integer quantity,
    @JsonProperty("precioUnit")
    BigDecimal unitPrice,
    BigDecimal total,
    @JsonProperty("cliente")
    String customer,
    LocalDateTime createdAt) {}
