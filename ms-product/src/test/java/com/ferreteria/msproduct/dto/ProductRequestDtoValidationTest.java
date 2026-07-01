package com.ferreteria.msproduct.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validDto_hasNoViolations() {
        ProductRequestDto dto = new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas");
        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidDto_reportsExpectedFields() {
        ProductRequestDto dto = new ProductRequestDto("", "desc", null, "");
        var violations = validator.validate(dto);
        Set<String> fields = violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());

        assertEquals(2, violations.size());
        assertTrue(fields.contains("nombre"));
        assertTrue(fields.contains("precio"));
    }
}
