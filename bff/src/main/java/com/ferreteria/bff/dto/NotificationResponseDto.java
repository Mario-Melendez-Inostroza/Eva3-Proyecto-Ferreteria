package com.ferreteria.bff.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;
public record NotificationResponseDto(
    UUID id,
    @JsonProperty("tipo")
    String type,
    @JsonProperty("mensaje")
    String message,
    UUID productId,
    @JsonProperty("leida")
    Boolean read,
    LocalDateTime createdAt) {}
