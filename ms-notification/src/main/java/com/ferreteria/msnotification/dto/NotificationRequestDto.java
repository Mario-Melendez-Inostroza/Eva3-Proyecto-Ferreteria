package com.ferreteria.msnotification.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record NotificationRequestDto(
    @NotBlank(message = "Tipo es obligatorio") String tipo,
    @NotBlank(message = "Mensaje es obligatorio") String mensaje,
    UUID productId
) {}
