package com.ferreteria.msproduct.controller;

import com.ferreteria.msproduct.dto.*;
import com.ferreteria.msproduct.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> listar() {
        return ResponseEntity.ok(productService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponseDto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productService.buscarPorNombre(nombre));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductResponseDto>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productService.buscarPorCategoria(categoria));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> crear(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> actualizar(
            @PathVariable UUID id, @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> desactivar(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.desactivar(id));
    }
}
