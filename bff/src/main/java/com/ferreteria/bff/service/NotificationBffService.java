package com.ferreteria.bff.service;

import com.ferreteria.bff.client.NotificationClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationBffService {

    private final NotificationClient notificationClient;

    public List<NotificationResponseDto> findAll() {
        return notificationClient.findAll();
    }

    public List<NotificationResponseDto> findUnread() {
        return notificationClient.findUnread();
    }

    public NotificationResponseDto markAsRead(UUID id) {
        return notificationClient.markAsRead(id);
    }

    public MessageResponseDto markAllAsRead() {
        return notificationClient.markAllAsRead();
    }
}
