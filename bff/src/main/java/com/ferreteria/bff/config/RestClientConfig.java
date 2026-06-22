package com.ferreteria.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${auth.service.url}")        private String authUrl;
    @Value("${product.service.url}")     private String productUrl;
    @Value("${inventory.service.url}")   private String inventoryUrl;
    @Value("${sales.service.url}")       private String salesUrl;
    @Value("${notification.service.url}") private String notificationUrl;

    @Bean("authRestClient")
    public RestClient authRestClient() {
        return RestClient.builder().baseUrl(authUrl).build();
    }

    @Bean("productRestClient")
    public RestClient productRestClient() {
        return RestClient.builder().baseUrl(productUrl).build();
    }

    @Bean("inventoryRestClient")
    public RestClient inventoryRestClient() {
        return RestClient.builder().baseUrl(inventoryUrl).build();
    }

    @Bean("salesRestClient")
    public RestClient salesRestClient() {
        return RestClient.builder().baseUrl(salesUrl).build();
    }

    @Bean("notificationRestClient")
    public RestClient notificationRestClient() {
        return RestClient.builder().baseUrl(notificationUrl).build();
    }
}
