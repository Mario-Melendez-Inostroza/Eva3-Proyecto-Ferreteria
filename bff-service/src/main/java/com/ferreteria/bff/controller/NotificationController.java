package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.BffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final BffService bffService;

    // GET /api/notifications → listar todas las notificaciones
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> listar(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.listarNotificaciones(authHeader));
    }

    // GET /api/notifications/no-leidas → solo notificaciones no leídas
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificationResponseDto>> noLeidas(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.notificacionesNoLeidas(authHeader));
    }

    // PUT /api/notifications/{id}/leer → marcar una notificación como leída
    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificationResponseDto> marcarLeida(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id) {
        return ResponseEntity.ok(bffService.marcarLeida(authHeader, id));
    }

    // PUT /api/notifications/leer-todas → marcar todas como leídas
    @PutMapping("/leer-todas")
    public ResponseEntity<MessageResponseDto> marcarTodasLeidas(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.marcarTodasLeidas(authHeader));
    }
}
