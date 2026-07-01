package com.ferreteria.msnotification.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationRequestDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationForValidData() {
        NotificationRequestDto dto = new NotificationRequestDto("STOCK_BAJO", "Stock bajo", UUID.randomUUID());

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailValidationWhenTipoAndMensajeAreBlank() {
        NotificationRequestDto dto = new NotificationRequestDto("", "", UUID.randomUUID());

        var violations = validator.validate(dto);
        Set<String> fields = violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());

        assertEquals(2, violations.size());
        assertTrue(fields.contains("tipo"));
        assertTrue(fields.contains("mensaje"));
    }

    @Test
    void shouldFailValidationWithExpectedMessageWhenTipoIsBlank() {
        NotificationRequestDto dto = new NotificationRequestDto("", "Stock bajo", UUID.randomUUID());

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Tipo es obligatorio", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWithExpectedMessageWhenMensajeIsBlank() {
        NotificationRequestDto dto = new NotificationRequestDto("STOCK_BAJO", "", UUID.randomUUID());

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Mensaje es obligatorio", violations.iterator().next().getMessage());
    }
}
