package com.ferreteria.bff.dto;
import jakarta.validation.constraints.*;
public record RegisterRequestDto(
    @NotBlank(message = "Username is required") String username,
    @NotBlank @Size(min = 6, message = "Minimum 6 characters") String password) {}
