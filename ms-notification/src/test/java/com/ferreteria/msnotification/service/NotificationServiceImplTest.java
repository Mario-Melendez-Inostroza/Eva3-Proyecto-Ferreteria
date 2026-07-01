package com.ferreteria.msnotification.service;

import com.ferreteria.msnotification.dto.MessageResponseDto;
import com.ferreteria.msnotification.dto.NotificationRequestDto;
import com.ferreteria.msnotification.dto.NotificationResponseDto;
import com.ferreteria.msnotification.exception.NotificationNotFoundException;
import com.ferreteria.msnotification.model.Notification;
import com.ferreteria.msnotification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void crearShouldSaveNotification() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        UUID productId = UUID.randomUUID();
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto result = service.crear(new NotificationRequestDto("STOCK_BAJO", "Stock bajo", productId));

        assertNotNull(result);
        assertEquals("STOCK_BAJO", result.tipo());
        assertEquals("Stock bajo", result.mensaje());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void listarTodasShouldReturnAllNotifications() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        Notification notification = Notification.builder().tipo("VENTA_REGISTRADA").mensaje("Venta registrada")
                .leida(false).createdAt(LocalDateTime.now()).build();
        when(notificationRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(notification));

        List<NotificationResponseDto> result = service.listarTodas();

        assertEquals(1, result.size());
        assertEquals("VENTA_REGISTRADA", result.get(0).tipo());
    }

    @Test
    void listarNoLeidasShouldReturnUnreadNotifications() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        Notification notification = Notification.builder().tipo("STOCK_BAJO").mensaje("Stock bajo")
                .leida(false).createdAt(LocalDateTime.now()).build();
        when(notificationRepository.findByLeidaFalseOrderByCreatedAtDesc()).thenReturn(List.of(notification));

        List<NotificationResponseDto> result = service.listarNoLeidas();

        assertEquals(1, result.size());
        assertEquals(false, result.get(0).leida());
    }

    @Test
    void listarPorTipoShouldReturnMatchingNotifications() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        Notification notification = Notification.builder().tipo("STOCK_BAJO").mensaje("Stock bajo")
                .leida(false).createdAt(LocalDateTime.now()).build();
        when(notificationRepository.findByTipoOrderByCreatedAtDesc("STOCK_BAJO")).thenReturn(List.of(notification));

        List<NotificationResponseDto> result = service.listarPorTipo("STOCK_BAJO");

        assertEquals(1, result.size());
        assertEquals("STOCK_BAJO", result.get(0).tipo());
    }

    @Test
    void marcarLeidaShouldMarkNotificationAsRead() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        UUID id = UUID.randomUUID();
        Notification notification = Notification.builder().id(id).tipo("STOCK_BAJO").mensaje("Stock bajo")
                .leida(false).createdAt(LocalDateTime.now()).build();
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto result = service.marcarLeida(id);

        assertEquals(true, result.leida());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void marcarLeidaShouldThrowWhenNotFound() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        UUID id = UUID.randomUUID();
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());

        NotificationNotFoundException ex = assertThrows(NotificationNotFoundException.class,
                () -> service.marcarLeida(id));

        assertNotNull(ex.getMessage());
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void marcarTodasLeidasShouldMarkAllUnreadNotifications() {
        NotificationServiceImpl service = new NotificationServiceImpl(notificationRepository);
        Notification notification = Notification.builder().tipo("STOCK_BAJO").mensaje("Stock bajo")
                .leida(false).createdAt(LocalDateTime.now()).build();
        when(notificationRepository.findByLeidaFalseOrderByCreatedAtDesc()).thenReturn(List.of(notification));

        MessageResponseDto result = service.marcarTodasLeidas();

        assertEquals("Se marcaron 1 notificaciones como leidas", result.message());
        verify(notificationRepository).saveAll(List.of(notification));
    }
}
