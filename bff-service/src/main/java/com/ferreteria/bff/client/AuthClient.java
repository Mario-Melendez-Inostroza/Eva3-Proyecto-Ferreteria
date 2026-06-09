package com.ferreteria.bff.client;

import com.ferreteria.bff.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthClient {

    private final RestClient restClient;

    public AuthClient(@Qualifier("authRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public MessageResponseDto register(RegisterRequestDto dto) {
        return restClient.post().uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(MessageResponseDto.class);
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        return restClient.post().uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(AuthResponseDto.class);
    }

    public ValidateTokenResponseDto validateToken(String token) {
        return restClient.post().uri("/auth/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ValidateTokenRequestDto(token))
                .retrieve().body(ValidateTokenResponseDto.class);
    }
}
