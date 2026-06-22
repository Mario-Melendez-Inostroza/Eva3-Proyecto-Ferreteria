package com.ferreteria.bff.service;

import com.ferreteria.bff.client.AuthClient;
import com.ferreteria.bff.dto.AuthResponseDto;
import com.ferreteria.bff.dto.LoginRequestDto;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthBffService {

    private final AuthClient authClient;

    public MessageResponseDto register(RegisterRequestDto request) {
        return authClient.register(request);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        return authClient.login(request);
    }
}
