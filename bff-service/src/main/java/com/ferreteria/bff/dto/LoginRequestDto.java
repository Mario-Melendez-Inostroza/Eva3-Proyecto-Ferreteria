package com.ferreteria.bff.dto;
import jakarta.validation.constraints.NotBlank;
public record LoginRequestDto(
    @NotBlank(message = "Username obligatorio") String username,
    @NotBlank(message = "Password obligatorio") String password) {}
