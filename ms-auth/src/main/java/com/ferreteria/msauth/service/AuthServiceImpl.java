package com.ferreteria.msauth.service;

import com.ferreteria.msauth.dto.*;
import com.ferreteria.msauth.exception.UnauthorizedException;
import com.ferreteria.msauth.model.User;
import com.ferreteria.msauth.repository.UserRepository;
import com.ferreteria.msauth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public MessageResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new UnauthorizedException("El usuario ya existe: " + dto.username());
        }
        userRepository.save(User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build());
        return new MessageResponseDto("Usuario registrado correctamente: " + dto.username());
    }

    @Override
    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        return new AuthResponseDto(jwtUtil.generateToken(user.getUsername()), user.getUsername());
    }

    @Override
    public ValidateTokenResponseDto validateToken(ValidateTokenRequestDto dto) {
        if (!jwtUtil.validateToken(dto.token())) {
            return new ValidateTokenResponseDto(false, null);
        }
        return new ValidateTokenResponseDto(true, jwtUtil.getUsername(dto.token()));
    }
}
