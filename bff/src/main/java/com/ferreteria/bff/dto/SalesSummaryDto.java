package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
public record SalesSummaryDto(
    @JsonProperty("totalVentas")
    Long totalSales,
    @JsonProperty("montoTotal")
    BigDecimal totalAmount) {}
