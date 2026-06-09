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
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(@Qualifier("productRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ProductResponseDto> listar() {
        return restClient.get().uri("/products").retrieve()
                .body(new ParameterizedTypeReference<List<ProductResponseDto>>() {});
    }

    public ProductResponseDto buscarPorId(UUID id) {
        return restClient.get().uri("/products/{id}", id).retrieve()
                .body(ProductResponseDto.class);
    }

    public List<ProductResponseDto> buscarPorNombre(String nombre) {
        return restClient.get().uri("/products/buscar?nombre={nombre}", nombre).retrieve()
                .body(new ParameterizedTypeReference<List<ProductResponseDto>>() {});
    }

    public List<ProductResponseDto> buscarPorCategoria(String categoria) {
        return restClient.get().uri("/products/categoria/{categoria}", categoria).retrieve()
                .body(new ParameterizedTypeReference<List<ProductResponseDto>>() {});
    }

    public ProductResponseDto crear(ProductRequestDto dto) {
        return restClient.post().uri("/products")
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(ProductResponseDto.class);
    }

    public ProductResponseDto actualizar(UUID id, ProductRequestDto dto) {
        return restClient.put().uri("/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON).body(dto)
                .retrieve().body(ProductResponseDto.class);
    }

    public MessageResponseDto desactivar(UUID id) {
        return restClient.delete().uri("/products/{id}", id)
                .retrieve().body(MessageResponseDto.class);
    }
}
