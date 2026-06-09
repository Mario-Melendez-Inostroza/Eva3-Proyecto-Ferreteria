package com.ferreteria.mssales.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    @Bean("productRestClient")
    public RestClient productRestClient() {
        return RestClient.builder().baseUrl(productServiceUrl).build();
    }

    @Bean("inventoryRestClient")
    public RestClient inventoryRestClient() {
        return RestClient.builder().baseUrl(inventoryServiceUrl).build();
    }

    @Bean("notificationRestClient")
    public RestClient notificationRestClient() {
        return RestClient.builder().baseUrl(notificationServiceUrl).build();
    }
}
