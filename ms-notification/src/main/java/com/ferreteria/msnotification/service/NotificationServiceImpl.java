package com.ferreteria.msnotification.service;

import com.ferreteria.msnotification.dto.*;
import com.ferreteria.msnotification.exception.NotificationNotFoundException;
import com.ferreteria.msnotification.model.Notification;
import com.ferreteria.msnotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private NotificationResponseDto toDto(Notification n) {
        return new NotificationResponseDto(
                n.getId(), n.getTipo(), n.getMensaje(),
                n.getProductId(), n.getLeida(), n.getCreatedAt());
    }

    @Override
    public NotificationResponseDto crear(NotificationRequestDto dto) {
        Notification saved = notificationRepository.save(Notification.builder()
                .tipo(dto.tipo())
                .mensaje(dto.mensaje())
                .productId(dto.productId())
                .build());
        return toDto(saved);
    }

    @Override
    public List<NotificationResponseDto> listarTodas() {
        return notificationRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> listarNoLeidas() {
        return notificationRepository.findByLeidaFalseOrderByCreatedAtDesc()
                .stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> listarPorTipo(String tipo) {
        return notificationRepository.findByTipoOrderByCreatedAtDesc(tipo)
                .stream().map(this::toDto).toList();
    }

    @Override
    public NotificationResponseDto marcarLeida(UUID id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notificacion no encontrada: " + id));
        n.setLeida(true);
        return toDto(notificationRepository.save(n));
    }

    @Override
    public MessageResponseDto marcarTodasLeidas() {
        List<Notification> noLeidas = notificationRepository.findByLeidaFalseOrderByCreatedAtDesc();
        noLeidas.forEach(n -> n.setLeida(true));
        notificationRepository.saveAll(noLeidas);
        return new MessageResponseDto("Se marcaron " + noLeidas.size() + " notificaciones como leidas");
    }
}
