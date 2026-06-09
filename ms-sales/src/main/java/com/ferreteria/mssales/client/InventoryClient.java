package com.ferreteria.mssales.client;

import com.ferreteria.mssales.dto.InventoryDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.UUID;

// Cliente HTTP para ms-inventory
@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(@Qualifier("inventoryRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    // Descuenta stock en ms-inventory al registrar una venta
    public InventoryDto descontarStock(UUID productId, Integer cantidad) {
        return restClient.put()
                .uri("/inventory/producto/{productId}/descontar", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("cantidad", cantidad))
                .retrieve()
                .body(InventoryDto.class);
    }
}
