package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.NotificationResponseDto;
import com.ferreteria.bff.service.NotificationBffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationBffService notificationBffService;

    @Test
    void findAllShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationBffService);
        when(notificationBffService.findAll()).thenReturn(List.of(response(false)));

        ResponseEntity<List<NotificationResponseDto>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findUnreadShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationBffService);
        when(notificationBffService.findUnread()).thenReturn(List.of(response(false)));

        ResponseEntity<List<NotificationResponseDto>> result = controller.findUnread();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(false, result.getBody().get(0).read());
    }

    @Test
    void markAsReadShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationBffService);
        UUID id = UUID.randomUUID();
        when(notificationBffService.markAsRead(id)).thenReturn(response(true));

        ResponseEntity<NotificationResponseDto> result = controller.markAsRead(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(true, result.getBody().read());
    }

    @Test
    void markAllAsReadShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationBffService);
        when(notificationBffService.markAllAsRead()).thenReturn(new MessageResponseDto("ok"));

        ResponseEntity<MessageResponseDto> result = controller.markAllAsRead();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("ok", result.getBody().message());
    }

    private NotificationResponseDto response(boolean read) {
        return new NotificationResponseDto(UUID.randomUUID(), "INFO", "mensaje", UUID.randomUUID(), read,
                LocalDateTime.now());
    }
}
