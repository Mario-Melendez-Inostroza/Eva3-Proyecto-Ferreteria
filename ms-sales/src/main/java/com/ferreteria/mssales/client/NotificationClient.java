package com.ferreteria.mssales.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.UUID;

// Cliente HTTP para ms-notification
@Component
public class NotificationClient {

    private final RestClient restClient;

    public NotificationClient(@Qualifier("notificationRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    // Envía alerta de stock bajo a ms-notification
    public void notificarStockBajo(UUID productId, String nombreProducto, int stockActual) {
        restClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "tipo", "STOCK_BAJO",
                        "mensaje", "Stock bajo para '" + nombreProducto
                                + "'. Stock actual: " + stockActual,
                        "productId", productId.toString()
                ))
                .retrieve()
                .toBodilessEntity();
    }

    // Envía confirmación de venta registrada
    public void notificarVenta(UUID productId, String nombreProducto, int cantidad) {
        restClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "tipo", "VENTA_REGISTRADA",
                        "mensaje", "Venta registrada: " + cantidad + " unidades de '" + nombreProducto + "'",
                        "productId", productId.toString()
                ))
                .retrieve()
                .toBodilessEntity();
    }
}
