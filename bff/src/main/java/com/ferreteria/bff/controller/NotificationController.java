package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.NotificationBffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints for notification management")
public class NotificationController {

    private final NotificationBffService notificationBffService;

    @Operation(
            summary = "List notifications",
            description = "Returns all notifications."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> findAll() {
        return ResponseEntity.ok(notificationBffService.findAll());
    }

    @Operation(
            summary = "List unread notifications",
            description = "Returns notifications that have not been marked as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unread notifications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificationResponseDto>> findUnread() {
        return ResponseEntity.ok(notificationBffService.findUnread());
    }

    @Operation(
            summary = "Mark notification as read",
            description = "Marks the notification identified by the provided ID as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "400", description = "Invalid notification identifier"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificationResponseDto> markAsRead(
            @Parameter(description = "Notification unique identifier", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(notificationBffService.markAsRead(id));
    }

    @Operation(
            summary = "Mark all notifications as read",
            description = "Marks every unread notification as read."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All notifications marked as read"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/leer-todas")
    public ResponseEntity<MessageResponseDto> markAllAsRead() {
        return ResponseEntity.ok(notificationBffService.markAllAsRead());
    }
}
