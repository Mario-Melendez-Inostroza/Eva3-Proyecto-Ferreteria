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
public class SalesClient {

    private final RestClient restClient;

    public SalesClient(@Qualifier("salesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public SaleResponseDto register(SaleRequestDto dto) {
        return restClient.post().uri("/sales")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(SaleResponseDto.class);
    }

    public List<SaleResponseDto> findAll() {
        return restClient.get().uri("/sales").retrieve()
                .body(new ParameterizedTypeReference<List<SaleResponseDto>>() {});
    }

    public SaleResponseDto findById(UUID id) {
        return restClient.get().uri("/sales/{id}", id).retrieve()
                .body(SaleResponseDto.class);
    }

    public List<SaleResponseDto> findByProduct(UUID productId) {
        return restClient.get().uri("/sales/producto/{productId}", productId).retrieve()
                .body(new ParameterizedTypeReference<List<SaleResponseDto>>() {});
    }

    public SalesSummaryDto summary() {
        return restClient.get().uri("/sales/resumen").retrieve()
                .body(SalesSummaryDto.class);
    }
}
