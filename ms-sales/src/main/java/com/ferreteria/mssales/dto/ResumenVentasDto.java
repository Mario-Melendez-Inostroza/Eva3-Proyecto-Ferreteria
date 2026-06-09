package com.ferreteria.mssales.dto;

import java.math.BigDecimal;

public record ResumenVentasDto(
    Long totalVentas,
    BigDecimal montoTotal
) {}
