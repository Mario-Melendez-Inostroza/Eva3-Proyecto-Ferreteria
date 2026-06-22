package com.ferreteria.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        @JsonProperty("nombre")
        String name,
        @JsonProperty("descripcion")
        String description,
        @JsonProperty("precio")
        BigDecimal price,
        @JsonProperty("categoria")
        String category,
        @JsonProperty("activo")
        Boolean active,
        LocalDateTime createdAt) {
}
