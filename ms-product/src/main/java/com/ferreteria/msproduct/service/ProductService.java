package com.ferreteria.msproduct.service;

import com.ferreteria.msproduct.dto.*;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDto> listarActivos();
    ProductResponseDto buscarPorId(UUID id);
    List<ProductResponseDto> buscarPorNombre(String nombre);
    List<ProductResponseDto> buscarPorCategoria(String categoria);
    ProductResponseDto crear(ProductRequestDto dto);
    ProductResponseDto actualizar(UUID id, ProductRequestDto dto);
    MessageResponseDto desactivar(UUID id);
}
