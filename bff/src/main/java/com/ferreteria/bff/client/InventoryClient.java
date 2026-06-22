package com.ferreteria.bff.client;

import com.ferreteria.bff.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.UUID;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(@Qualifier("inventoryRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public InventoryResponseDto create(InventoryRequestDto dto) {
        return restClient.post().uri("/inventory")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(InventoryResponseDto.class);
    }

    public List<InventoryResponseDto> findAll() {
        return restClient.get().uri("/inventory").retrieve()
                .body(new ParameterizedTypeReference<List<InventoryResponseDto>>() {});
    }

    public List<InventoryResponseDto> findLowStock() {
        return restClient.get().uri("/inventory/stock-bajo").retrieve()
                .body(new ParameterizedTypeReference<List<InventoryResponseDto>>() {});
    }

    public InventoryResponseDto findByProduct(UUID productId) {
        return restClient.get().uri("/inventory/producto/{productId}", productId)
                .retrieve().body(InventoryResponseDto.class);
    }

    public InventoryResponseDto addStock(UUID productId, StockAdjustmentDto dto) {
        return restClient.put().uri("/inventory/producto/{productId}/agregar", productId)
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(InventoryResponseDto.class);
    }

    public InventoryResponseDto subtractStock(UUID productId, StockAdjustmentDto dto) {
        return restClient.put().uri("/inventory/producto/{productId}/descontar", productId)
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(InventoryResponseDto.class);
    }
}
