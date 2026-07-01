package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.HealthResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HealthControllerTest {

    @Test
    void healthShouldReturnOk() {
        HealthController controller = new HealthController();

        ResponseEntity<HealthResponseDto> result = controller.health();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("OK", result.getBody().status());
    }
}
