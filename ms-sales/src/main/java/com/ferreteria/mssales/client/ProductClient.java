package com.ferreteria.mssales.client;

import com.ferreteria.mssales.dto.ProductDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.UUID;

// Cliente HTTP para ms-product
@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(@Qualifier("productRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    // Obtiene info del producto: nombre y precio actual
    public ProductDto buscarProducto(UUID productId) {
        return restClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .body(ProductDto.class);
    }
}
