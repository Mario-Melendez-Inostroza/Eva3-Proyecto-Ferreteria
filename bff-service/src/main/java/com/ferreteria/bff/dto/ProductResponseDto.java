package com.ferreteria.bff.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
public record ProductResponseDto(
    UUID id, String nombre, String descripcion,
    BigDecimal precio, String categoria,
    Boolean activo, LocalDateTime createdAt) {}
