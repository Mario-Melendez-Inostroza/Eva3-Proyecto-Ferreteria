package com.ferreteria.msnotification.service;

import com.ferreteria.msnotification.dto.*;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationResponseDto crear(NotificationRequestDto dto);
    List<NotificationResponseDto> listarTodas();
    List<NotificationResponseDto> listarNoLeidas();
    List<NotificationResponseDto> listarPorTipo(String tipo);
    NotificationResponseDto marcarLeida(UUID id);
    MessageResponseDto marcarTodasLeidas();
}
