package com.ferreteria.msnotification.repository;

import com.ferreteria.msnotification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByLeidaFalseOrderByCreatedAtDesc();
    List<Notification> findByTipoOrderByCreatedAtDesc(String tipo);
    List<Notification> findAllByOrderByCreatedAtDesc();
}
