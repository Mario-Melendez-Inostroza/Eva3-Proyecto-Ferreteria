package com.ferreteria.msauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
    @NotBlank(message = "Username es obligatorio") String username,
    @NotBlank(message = "Password es obligatorio")
    @Size(min = 6, message = "Password debe tener mínimo 6 caracteres") String password
) {}
