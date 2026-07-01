package com.ferreteria.msauth.service;

import com.ferreteria.msauth.dto.*;

/**
 * Business operations for user registration, authentication, and JWT token
 * validation.
 */
public interface AuthService {

    /**
     * Registers a new user with an encrypted password.
     *
     * @param dto the username and password to register
     * @return a confirmation message
     */
    MessageResponseDto register(RegisterRequestDto dto);

    /**
     * Authenticates a user and issues a signed JWT token.
     *
     * @param dto the login credentials
     * @return the generated token together with the username
     */
    AuthResponseDto login(LoginRequestDto dto);

    /**
     * Validates a JWT token previously issued by {@link #login}.
     *
     * @param dto the token to validate
     * @return whether the token is valid and, if so, its owning username
     */
    ValidateTokenResponseDto validateToken(ValidateTokenRequestDto dto);
}
