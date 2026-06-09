package com.ferreteria.msnotification.controller;

import com.ferreteria.msnotification.dto.*;
import com.ferreteria.msnotification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // POST /notifications → crear notificación (llamado internamente por ms-sales)
    @PostMapping
    public ResponseEntity<NotificationResponseDto> crear(@Valid @RequestBody NotificationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.crear(dto));
    }

    // GET /notifications → listar todas
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> listarTodas() {
        return ResponseEntity.ok(notificationService.listarTodas());
    }

    // GET /notifications/no-leidas → solo notificaciones no leídas
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificationResponseDto>> listarNoLeidas() {
        return ResponseEntity.ok(notificationService.listarNoLeidas());
    }

    // GET /notifications/tipo/{tipo} → filtrar por tipo (STOCK_BAJO, VENTA, etc.)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificationResponseDto>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(notificationService.listarPorTipo(tipo));
    }

    // PUT /notifications/{id}/leer → marcar una como leída
    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificationResponseDto> marcarLeida(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.marcarLeida(id));
    }

    // PUT /notifications/leer-todas → marcar todas como leídas
    @PutMapping("/leer-todas")
    public ResponseEntity<MessageResponseDto> marcarTodasLeidas() {
        return ResponseEntity.ok(notificationService.marcarTodasLeidas());
    }
}
