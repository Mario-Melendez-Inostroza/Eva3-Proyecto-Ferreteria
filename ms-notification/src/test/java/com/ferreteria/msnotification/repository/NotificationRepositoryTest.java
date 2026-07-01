package com.ferreteria.msnotification.repository;

import com.ferreteria.msnotification.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void shouldFindUnreadNotificationsOrderedByCreatedAtDesc() {
        UUID productId = UUID.randomUUID();
        Notification notification = Notification.builder()
                .tipo("STOCK_BAJO")
                .mensaje("Stock bajo")
                .productId(productId)
                .leida(false)
                .build();

        notificationRepository.save(notification);

        List<Notification> result = notificationRepository.findByLeidaFalseOrderByCreatedAtDesc();

        assertTrue(result.stream().anyMatch(n -> productId.equals(n.getProductId()) && "STOCK_BAJO".equals(n.getTipo())));
    }

    @Test
    void shouldFindNotificationsByTipo() {
        Notification notification = Notification.builder()
                .tipo("VENTA_REGISTRADA")
                .mensaje("Venta registrada")
                .productId(UUID.randomUUID())
                .leida(false)
                .build();

        notificationRepository.save(notification);

        List<Notification> result = notificationRepository.findByTipoOrderByCreatedAtDesc("VENTA_REGISTRADA");

        assertEquals(1, result.size());
        assertEquals("VENTA_REGISTRADA", result.get(0).getTipo());
    }

    @Test
    void shouldFindAllNotificationsOrderedByCreatedAtDesc() {
        UUID productId = UUID.randomUUID();
        Notification notification = Notification.builder()
                .tipo("STOCK_BAJO")
                .mensaje("Stock bajo")
                .productId(productId)
                .leida(false)
                .build();

        notificationRepository.save(notification);

        List<Notification> result = notificationRepository.findAllByOrderByCreatedAtDesc();

        assertTrue(result.stream().anyMatch(n -> productId.equals(n.getProductId())));
    }
}
