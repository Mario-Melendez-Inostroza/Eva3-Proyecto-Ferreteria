package com.ferreteria.mssales.dto;

import java.util.UUID;

// DTO que recibe de ms-inventory al consultar stock
public record InventoryDto(
    UUID id,
    UUID productId,
    Integer stock,
    Integer stockMinimo,
    boolean stockBajo
) {}
