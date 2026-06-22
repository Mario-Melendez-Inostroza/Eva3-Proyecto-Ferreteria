package com.ferreteria.bff.client;

import com.ferreteria.bff.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.UUID;

@Component
public class NotificationClient {

    private final RestClient restClient;

    public NotificationClient(@Qualifier("notificationRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<NotificationResponseDto> findAll() {
        return restClient.get().uri("/notifications").retrieve()
                .body(new ParameterizedTypeReference<List<NotificationResponseDto>>() {});
    }

    public List<NotificationResponseDto> findUnread() {
        return restClient.get().uri("/notifications/no-leidas").retrieve()
                .body(new ParameterizedTypeReference<List<NotificationResponseDto>>() {});
    }

    public NotificationResponseDto markAsRead(UUID id) {
        return restClient.put().uri("/notifications/{id}/leer", id)
                .retrieve().body(NotificationResponseDto.class);
    }

    public MessageResponseDto markAllAsRead() {
        return restClient.put().uri("/notifications/leer-todas")
                .retrieve().body(MessageResponseDto.class);
    }
}
