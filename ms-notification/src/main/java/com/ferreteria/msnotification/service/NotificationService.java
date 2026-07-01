package com.ferreteria.msnotification.service;

import com.ferreteria.msnotification.dto.*;
import java.util.List;
import java.util.UUID;

/**
 * Business operations for creating and querying internal system
 * notifications, such as low stock alerts and sale registration events.
 */
public interface NotificationService {

    /**
     * Creates a new notification.
     *
     * @param dto the notification data to create
     * @return the created notification
     */
    NotificationResponseDto crear(NotificationRequestDto dto);

    /**
     * Lists all notifications.
     *
     * @return every notification registered in the system
     */
    List<NotificationResponseDto> listarTodas();

    /**
     * Lists notifications that have not been marked as read.
     *
     * @return every unread notification
     */
    List<NotificationResponseDto> listarNoLeidas();

    /**
     * Lists notifications matching a given type.
     *
     * @param tipo the notification type to filter by
     * @return the notifications matching the provided type
     */
    List<NotificationResponseDto> listarPorTipo(String tipo);

    /**
     * Marks a single notification as read.
     *
     * @param id the unique identifier of the notification to mark as read
     * @return the updated notification
     */
    NotificationResponseDto marcarLeida(UUID id);

    /**
     * Marks every unread notification as read.
     *
     * @return a confirmation message with the number of notifications updated
     */
    MessageResponseDto marcarTodasLeidas();
}
