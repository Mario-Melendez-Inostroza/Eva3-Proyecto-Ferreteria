package com.ferreteria.msauth.service;

import com.ferreteria.msauth.dto.AuthResponseDto;
import com.ferreteria.msauth.dto.LoginRequestDto;
import com.ferreteria.msauth.dto.MessageResponseDto;
import com.ferreteria.msauth.dto.RegisterRequestDto;
import com.ferreteria.msauth.dto.ValidateTokenRequestDto;
import com.ferreteria.msauth.dto.ValidateTokenResponseDto;
import com.ferreteria.msauth.exception.InvalidCredentialsException;
import com.ferreteria.msauth.exception.UserAlreadyExistsException;
import com.ferreteria.msauth.model.User;
import com.ferreteria.msauth.repository.UserRepository;
import com.ferreteria.msauth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void registerShouldThrowWhenUserExists() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        when(userRepository.existsByUsername("usuario")).thenReturn(true);

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
                () -> service.register(new RegisterRequestDto("usuario", "password123")));

        assertNotNull(ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerShouldSaveAndReturnMessage() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        when(userRepository.existsByUsername("usuario")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MessageResponseDto result = service.register(new RegisterRequestDto("usuario", "password123"));

        assertNotNull(result);
        assertEquals("Usuario registrado correctamente: usuario", result.message());
    }

    @Test
    void loginShouldThrowWhenCredentialsAreInvalid() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        User user = new User();
        user.setUsername("usuario");
        user.setPassword("encoded");
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.empty());

        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class,
                () -> service.login(new LoginRequestDto("usuario", "password123")));

        assertNotNull(ex.getMessage());
    }

    @Test
    void loginShouldReturnTokenWhenCredentialsAreValid() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        User user = User.builder().id(UUID.randomUUID()).username("usuario").password("encoded").build();
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded")).thenReturn(true);
        when(jwtUtil.generateToken("usuario")).thenReturn("generated-token");

        AuthResponseDto result = service.login(new LoginRequestDto("usuario", "password123"));

        assertEquals("generated-token", result.token());
        assertEquals("usuario", result.username());
    }

    @Test
    void validateTokenShouldReturnFalseWhenTokenIsInvalid() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        when(jwtUtil.validateToken("bad-token")).thenReturn(false);

        ValidateTokenResponseDto result = service.validateToken(new ValidateTokenRequestDto("bad-token"));

        assertEquals(false, result.valid());
        assertEquals(null, result.username());
    }

    @Test
    void validateTokenShouldReturnUsernameWhenTokenIsValid() {
        AuthServiceImpl service = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
        when(jwtUtil.validateToken("good-token")).thenReturn(true);
        when(jwtUtil.getUsername("good-token")).thenReturn("usuario");

        ValidateTokenResponseDto result = service.validateToken(new ValidateTokenRequestDto("good-token"));

        assertEquals(true, result.valid());
        assertEquals("usuario", result.username());
    }
}
