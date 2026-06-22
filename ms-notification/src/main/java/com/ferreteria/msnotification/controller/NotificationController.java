package com.ferreteria.msnotification.controller;

import com.ferreteria.msnotification.dto.*;
import com.ferreteria.msnotification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notifications", description = "Endpoints for notification management")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "Create notification",
            description = "Creates a notification. This endpoint is used internally by the sales service.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Notification data to create",
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid notification request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<NotificationResponseDto> crear(@Valid @RequestBody NotificationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.crear(dto));
    }

    @Operation(
            summary = "List notifications",
            description = "Returns all notifications."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> listarTodas() {
        return ResponseEntity.ok(notificationService.listarTodas());
    }

    @Operation(
            summary = "List unread notifications",
            description = "Returns notifications that have not been marked as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unread notifications retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificationResponseDto>> listarNoLeidas() {
        return ResponseEntity.ok(notificationService.listarNoLeidas());
    }

    @Operation(
            summary = "List notifications by type",
            description = "Returns notifications matching the provided notification type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid notification type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificationResponseDto>> listarPorTipo(
            @Parameter(description = "Notification type", required = true)
            @PathVariable String tipo) {
        return ResponseEntity.ok(notificationService.listarPorTipo(tipo));
    }

    @Operation(
            summary = "Mark notification as read",
            description = "Marks the notification identified by the provided ID as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "400", description = "Invalid notification identifier"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificationResponseDto> marcarLeida(
            @Parameter(description = "Notification unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.marcarLeida(id));
    }

    @Operation(
            summary = "Mark all notifications as read",
            description = "Marks every unread notification as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All notifications marked as read"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/leer-todas")
    public ResponseEntity<MessageResponseDto> marcarTodasLeidas() {
        return ResponseEntity.ok(notificationService.marcarTodasLeidas());
    }
}
