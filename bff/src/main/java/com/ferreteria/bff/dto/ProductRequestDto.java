package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductRequestDto(
    @JsonProperty("nombre")
    @NotBlank(message = "Name is required") String name,
    @JsonProperty("descripcion")
    String description,
    @NotNull(message = "Price is required")
    @DecimalMin(value="0.01", message="Price must be greater than 0")
    @JsonProperty("precio")
    BigDecimal price,
    @JsonProperty("categoria")
    String category) {}
