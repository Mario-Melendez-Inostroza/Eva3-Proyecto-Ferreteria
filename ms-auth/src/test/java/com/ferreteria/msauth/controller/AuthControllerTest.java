package com.ferreteria.msauth.controller;

import com.ferreteria.msauth.dto.AuthResponseDto;
import com.ferreteria.msauth.dto.LoginRequestDto;
import com.ferreteria.msauth.dto.MessageResponseDto;
import com.ferreteria.msauth.dto.RegisterRequestDto;
import com.ferreteria.msauth.dto.ValidateTokenRequestDto;
import com.ferreteria.msauth.dto.ValidateTokenResponseDto;
import com.ferreteria.msauth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Test
    void registerShouldReturnCreated() {
        AuthController controller = new AuthController(authService);
        RegisterRequestDto request = new RegisterRequestDto("usuario", "password123");
        MessageResponseDto response = new MessageResponseDto("Usuario registrado correctamente: usuario");
        when(authService.register(any())).thenReturn(response);

        ResponseEntity<MessageResponseDto> result = controller.register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Usuario registrado correctamente: usuario", result.getBody().message());
    }

    @Test
    void loginShouldReturnOk() {
        AuthController controller = new AuthController(authService);
        LoginRequestDto request = new LoginRequestDto("usuario", "password123");
        AuthResponseDto response = new AuthResponseDto("token", "usuario");
        when(authService.login(any())).thenReturn(response);

        ResponseEntity<AuthResponseDto> result = controller.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("token", result.getBody().token());
    }

    @Test
    void validateShouldReturnOk() {
        AuthController controller = new AuthController(authService);
        ValidateTokenRequestDto request = new ValidateTokenRequestDto("token");
        ValidateTokenResponseDto response = new ValidateTokenResponseDto(true, "usuario");
        when(authService.validateToken(any())).thenReturn(response);

        ResponseEntity<ValidateTokenResponseDto> result = controller.validate(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().valid());
    }
}
