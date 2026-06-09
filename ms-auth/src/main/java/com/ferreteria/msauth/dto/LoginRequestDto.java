package com.ferreteria.msauth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank(message = "Username es obligatorio") String username,
    @NotBlank(message = "Password es obligatorio") String password
) {}
