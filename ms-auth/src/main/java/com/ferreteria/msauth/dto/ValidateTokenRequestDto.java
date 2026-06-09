package com.ferreteria.msauth.dto;
import jakarta.validation.constraints.NotBlank;
public record ValidateTokenRequestDto(@NotBlank String token) {}
