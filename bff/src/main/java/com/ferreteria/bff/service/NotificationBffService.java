package com.ferreteria.bff.service;

import com.ferreteria.bff.client.NotificationClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * BFF service for notification operations. Contains no business logic of
 * its own; it simply delegates each call to {@link NotificationClient}, the
 * HTTP client for the notification microservice.
 */
@Service
@RequiredArgsConstructor
public class NotificationBffService {

    private final NotificationClient notificationClient;

    /**
     * Delegates the retrieval of all notifications to the notification
     * microservice.
     *
     * @return the list of notifications
     */
    public List<NotificationResponseDto> findAll() {
        return notificationClient.findAll();
    }

    /**
     * Delegates the retrieval of unread notifications to the notification
     * microservice.
     *
     * @return the list of unread notifications
     */
    public List<NotificationResponseDto> findUnread() {
        return notificationClient.findUnread();
    }

    /**
     * Delegates marking a notification as read to the notification
     * microservice.
     *
     * @param id the notification unique identifier
     * @return the updated notification
     */
    public NotificationResponseDto markAsRead(UUID id) {
        return notificationClient.markAsRead(id);
    }

    /**
     * Delegates marking every unread notification as read to the
     * notification microservice.
     *
     * @return a confirmation message
     */
    public MessageResponseDto markAllAsRead() {
        return notificationClient.markAllAsRead();
    }
}
