package com.ferreteria.msproduct.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequestDto(
    @NotBlank(message = "Nombre es obligatorio") String nombre,
    String descripcion,
    @NotNull(message = "Precio es obligatorio")
    @DecimalMin(value = "0.01", message = "Precio debe ser mayor a 0") BigDecimal precio,
    String categoria
) {}
