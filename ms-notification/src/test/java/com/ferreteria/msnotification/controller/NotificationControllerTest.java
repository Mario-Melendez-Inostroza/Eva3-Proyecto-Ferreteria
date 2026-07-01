package com.ferreteria.msnotification.controller;

import com.ferreteria.msnotification.dto.MessageResponseDto;
import com.ferreteria.msnotification.dto.NotificationRequestDto;
import com.ferreteria.msnotification.dto.NotificationResponseDto;
import com.ferreteria.msnotification.service.NotificationService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Test
    void crearShouldReturnCreated() {
        NotificationController controller = new NotificationController(notificationService);
        NotificationRequestDto request = new NotificationRequestDto("STOCK_BAJO", "Stock bajo", UUID.randomUUID());
        NotificationResponseDto response = new NotificationResponseDto(UUID.randomUUID(), request.tipo(),
                request.mensaje(), request.productId(), false, LocalDateTime.now());
        when(notificationService.crear(any())).thenReturn(response);

        ResponseEntity<NotificationResponseDto> result = controller.crear(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("STOCK_BAJO", result.getBody().tipo());
    }

    @Test
    void listarTodasShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationService);
        NotificationResponseDto response = new NotificationResponseDto(UUID.randomUUID(), "VENTA_REGISTRADA",
                "Venta registrada", UUID.randomUUID(), false, LocalDateTime.now());
        when(notificationService.listarTodas()).thenReturn(List.of(response));

        ResponseEntity<List<NotificationResponseDto>> result = controller.listarTodas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void listarNoLeidasShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationService);
        NotificationResponseDto response = new NotificationResponseDto(UUID.randomUUID(), "STOCK_BAJO",
                "Stock bajo", UUID.randomUUID(), false, LocalDateTime.now());
        when(notificationService.listarNoLeidas()).thenReturn(List.of(response));

        ResponseEntity<List<NotificationResponseDto>> result = controller.listarNoLeidas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(false, result.getBody().get(0).leida());
    }

    @Test
    void listarPorTipoShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationService);
        NotificationResponseDto response = new NotificationResponseDto(UUID.randomUUID(), "STOCK_BAJO",
                "Stock bajo", UUID.randomUUID(), false, LocalDateTime.now());
        when(notificationService.listarPorTipo("STOCK_BAJO")).thenReturn(List.of(response));

        ResponseEntity<List<NotificationResponseDto>> result = controller.listarPorTipo("STOCK_BAJO");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("STOCK_BAJO", result.getBody().get(0).tipo());
    }

    @Test
    void marcarLeidaShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationService);
        UUID id = UUID.randomUUID();
        NotificationResponseDto response = new NotificationResponseDto(id, "STOCK_BAJO", "Stock bajo",
                UUID.randomUUID(), true, LocalDateTime.now());
        when(notificationService.marcarLeida(id)).thenReturn(response);

        ResponseEntity<NotificationResponseDto> result = controller.marcarLeida(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(true, result.getBody().leida());
    }

    @Test
    void marcarTodasLeidasShouldReturnOk() {
        NotificationController controller = new NotificationController(notificationService);
        MessageResponseDto response = new MessageResponseDto("Se marcaron 2 notificaciones como leidas");
        when(notificationService.marcarTodasLeidas()).thenReturn(response);

        ResponseEntity<MessageResponseDto> result = controller.marcarTodasLeidas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Se marcaron 2 notificaciones como leidas", result.getBody().message());
    }
}
