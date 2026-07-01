package com.ferreteria.bff.service;

import com.ferreteria.bff.client.NotificationClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.NotificationResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationBffServiceTest {

    @Mock
    private NotificationClient notificationClient;

    @Test
    void findAllShouldDelegateToClient() {
        NotificationBffService service = new NotificationBffService(notificationClient);
        when(notificationClient.findAll()).thenReturn(List.of(response(false)));

        List<NotificationResponseDto> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findUnreadShouldDelegateToClient() {
        NotificationBffService service = new NotificationBffService(notificationClient);
        when(notificationClient.findUnread()).thenReturn(List.of(response(false)));

        List<NotificationResponseDto> result = service.findUnread();

        assertEquals(false, result.get(0).read());
    }

    @Test
    void markAsReadShouldDelegateToClient() {
        NotificationBffService service = new NotificationBffService(notificationClient);
        UUID id = UUID.randomUUID();
        when(notificationClient.markAsRead(id)).thenReturn(response(true));

        NotificationResponseDto result = service.markAsRead(id);

        assertEquals(true, result.read());
    }

    @Test
    void markAllAsReadShouldDelegateToClient() {
        NotificationBffService service = new NotificationBffService(notificationClient);
        when(notificationClient.markAllAsRead()).thenReturn(new MessageResponseDto("ok"));

        MessageResponseDto result = service.markAllAsRead();

        assertEquals("ok", result.message());
    }

    private NotificationResponseDto response(boolean read) {
        return new NotificationResponseDto(UUID.randomUUID(), "INFO", "mensaje", UUID.randomUUID(), read,
                LocalDateTime.now());
    }
}
