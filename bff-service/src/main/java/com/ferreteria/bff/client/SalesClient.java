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

    public VentaResponseDto registrar(VentaRequestDto dto) {
        return restClient.post().uri("/sales")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(VentaResponseDto.class);
    }

    public List<VentaResponseDto> listar() {
        return restClient.get().uri("/sales").retrieve()
                .body(new ParameterizedTypeReference<List<VentaResponseDto>>() {});
    }

    public VentaResponseDto buscarPorId(UUID id) {
        return restClient.get().uri("/sales/{id}", id).retrieve()
                .body(VentaResponseDto.class);
    }

    public List<VentaResponseDto> listarPorProducto(UUID productId) {
        return restClient.get().uri("/sales/producto/{productId}", productId).retrieve()
                .body(new ParameterizedTypeReference<List<VentaResponseDto>>() {});
    }

    public ResumenVentasDto resumen() {
        return restClient.get().uri("/sales/resumen").retrieve()
                .body(ResumenVentasDto.class);
    }
}
