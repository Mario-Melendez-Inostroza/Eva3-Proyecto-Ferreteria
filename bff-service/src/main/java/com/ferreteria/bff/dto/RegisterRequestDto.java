package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
public record RegisterRequestDto(
    @NotBlank(message = "Username obligatorio") String username,
    @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres") String password) {}
