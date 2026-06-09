package com.ferreteria.bff.controller;

import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.service.BffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final BffService bffService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> listar(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(bffService.listarProductos(authHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> buscarPorId(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id) {
        return ResponseEntity.ok(bffService.buscarProducto(authHeader, id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponseDto>> buscarPorNombre(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String nombre) {
        return ResponseEntity.ok(bffService.buscarProductosPorNombre(authHeader, nombre));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductResponseDto>> buscarPorCategoria(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String categoria) {
        return ResponseEntity.ok(bffService.buscarProductosPorCategoria(authHeader, categoria));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> crear(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bffService.crearProducto(authHeader, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> actualizar(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(bffService.actualizarProducto(authHeader, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> desactivar(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id) {
        return ResponseEntity.ok(bffService.desactivarProducto(authHeader, id));
    }
}
