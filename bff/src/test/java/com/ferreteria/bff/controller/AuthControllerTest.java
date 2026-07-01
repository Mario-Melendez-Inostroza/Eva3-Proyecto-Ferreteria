package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.AuthResponseDto;
import com.ferreteria.bff.dto.LoginRequestDto;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.RegisterRequestDto;
import com.ferreteria.bff.service.AuthBffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthBffService authBffService;

    @Test
    void registerShouldReturnCreated() {
        AuthController controller = new AuthController(authBffService);
        RegisterRequestDto request = new RegisterRequestDto("usuario", "password123");
        MessageResponseDto response = new MessageResponseDto("Usuario registrado");
        when(authBffService.register(any())).thenReturn(response);

        ResponseEntity<MessageResponseDto> result = controller.register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void loginShouldReturnOk() {
        AuthController controller = new AuthController(authBffService);
        LoginRequestDto request = new LoginRequestDto("usuario", "password123");
        AuthResponseDto response = new AuthResponseDto("token", "usuario");
        when(authBffService.login(any())).thenReturn(response);

        ResponseEntity<AuthResponseDto> result = controller.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }
}
