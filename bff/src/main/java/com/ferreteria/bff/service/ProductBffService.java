package com.ferreteria.bff.service;

import com.ferreteria.bff.client.ProductClient;
import com.ferreteria.bff.dto.MessageResponseDto;
import com.ferreteria.bff.dto.ProductRequestDto;
import com.ferreteria.bff.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductBffService {

    private final ProductClient productClient;

    public List<ProductResponseDto> findAll() {
        return productClient.findAll();
    }

    public ProductResponseDto findById(UUID id) {
        return productClient.findById(id);
    }

    public List<ProductResponseDto> findByName(String name) {
        return productClient.findByName(name);
    }

    public List<ProductResponseDto> findByCategory(String category) {
        return productClient.findByCategory(category);
    }

    public ProductResponseDto create(ProductRequestDto request) {
        return productClient.create(request);
    }

    public ProductResponseDto update(UUID id, ProductRequestDto request) {
        return productClient.update(id, request);
    }

    public MessageResponseDto deactivate(UUID id) {
        return productClient.deactivate(id);
    }
}
