package com.ferreteria.mssales.dto;

import java.math.BigDecimal;
import java.util.UUID;

// DTO que recibe de ms-product al consultar un producto
public record ProductDto(
    UUID id,
    String nombre,
    BigDecimal precio,
    Boolean activo
) {}
