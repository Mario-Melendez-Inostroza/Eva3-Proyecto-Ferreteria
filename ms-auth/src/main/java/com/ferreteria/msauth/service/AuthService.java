package com.ferreteria.msauth.service;

import com.ferreteria.msauth.dto.*;

public interface AuthService {
    MessageResponseDto register(RegisterRequestDto dto);
    AuthResponseDto login(LoginRequestDto dto);
    ValidateTokenResponseDto validateToken(ValidateTokenRequestDto dto);
}
