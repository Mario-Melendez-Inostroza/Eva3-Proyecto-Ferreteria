package com.ferreteria.bff.service;

import com.ferreteria.bff.client.AuthClient;
import com.ferreteria.bff.dto.AuthResponseDto;
import com.ferreteria.bff.dto.LoginRequestDto;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.RegisterRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthBffServiceTest {

    @Mock
    private AuthClient authClient;

    @Test
    void registerShouldDelegateToClient() {
        AuthBffService service = new AuthBffService(authClient);
        RegisterRequestDto request = new RegisterRequestDto("usuario", "password123");
        when(authClient.register(request)).thenReturn(new MessageResponseDto("ok"));

        MessageResponseDto result = service.register(request);

        assertEquals("ok", result.message());
    }

    @Test
    void loginShouldDelegateToClient() {
        AuthBffService service = new AuthBffService(authClient);
        LoginRequestDto request = new LoginRequestDto("usuario", "password123");
        when(authClient.login(request)).thenReturn(new AuthResponseDto("token", "usuario"));

        AuthResponseDto result = service.login(request);

        assertEquals("token", result.token());
    }
}
