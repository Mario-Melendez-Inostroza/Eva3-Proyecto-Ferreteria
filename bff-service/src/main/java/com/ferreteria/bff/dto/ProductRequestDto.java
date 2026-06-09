package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductRequestDto(
    @NotBlank(message = "Nombre obligatorio") String nombre,
    String descripcion,
    @NotNull(message = "Precio obligatorio")
    @DecimalMin(value="0.01", message="Precio mayor a 0") BigDecimal precio,
    String categoria) {}
