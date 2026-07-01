package com.ferreteria.bff.service;

import com.ferreteria.bff.client.AuthClient;
import com.ferreteria.bff.dto.AuthResponseDto;
import com.ferreteria.bff.dto.LoginRequestDto;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BFF service for authentication operations. Contains no business logic of
 * its own; it simply delegates each call to {@link AuthClient}, the HTTP
 * client for the authentication microservice.
 */
@Service
@RequiredArgsConstructor
public class AuthBffService {

    private final AuthClient authClient;

    /**
     * Delegates user registration to the authentication microservice.
     *
     * @param request the username and password to register
     * @return a confirmation message
     */
    public MessageResponseDto register(RegisterRequestDto request) {
        return authClient.register(request);
    }

    /**
     * Delegates user authentication to the authentication microservice.
     *
     * @param request the login credentials
     * @return the generated token together with the username
     */
    public AuthResponseDto login(LoginRequestDto request) {
        return authClient.login(request);
    }
}
