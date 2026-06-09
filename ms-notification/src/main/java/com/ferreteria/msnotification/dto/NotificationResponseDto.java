package com.ferreteria.msnotification.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDto(
    UUID id,
    String tipo,
    String mensaje,
    UUID productId,
    Boolean leida,
    LocalDateTime createdAt
) {}
