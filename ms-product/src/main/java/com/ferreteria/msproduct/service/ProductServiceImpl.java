package com.ferreteria.msproduct.service;

import com.ferreteria.msproduct.dto.*;
import com.ferreteria.msproduct.exception.ResourceNotFoundException;
import com.ferreteria.msproduct.model.Product;
import com.ferreteria.msproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private ProductResponseDto toDto(Product p) {
        return new ProductResponseDto(p.getId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getCategoria(), p.getActivo(), p.getCreatedAt());
    }

    @Override
    public List<ProductResponseDto> listarActivos() {
        return productRepository.findByActivoTrue().stream().map(this::toDto).toList();
    }

    @Override
    public ProductResponseDto buscarPorId(UUID id) {
        return toDto(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id)));
    }

    @Override
    public List<ProductResponseDto> buscarPorNombre(String nombre) {
        return productRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre)
                .stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductResponseDto> buscarPorCategoria(String categoria) {
        return productRepository.findByCategoriaAndActivoTrue(categoria)
                .stream().map(this::toDto).toList();
    }

    @Override
    public ProductResponseDto crear(ProductRequestDto dto) {
        Product saved = productRepository.save(Product.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .categoria(dto.categoria())
                .build());
        return toDto(saved);
    }

    @Override
    public ProductResponseDto actualizar(UUID id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        product.setNombre(dto.nombre());
        product.setDescripcion(dto.descripcion());
        product.setPrecio(dto.precio());
        product.setCategoria(dto.categoria());
        return toDto(productRepository.save(product));
    }

    @Override
    public MessageResponseDto desactivar(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        product.setActivo(false);
        productRepository.save(product);
        return new MessageResponseDto("Producto desactivado: " + product.getNombre());
    }
}
