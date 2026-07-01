package com.ferreteria.msproduct.service;

import com.ferreteria.msproduct.dto.ProductRequestDto;
import com.ferreteria.msproduct.dto.ProductResponseDto;
import com.ferreteria.msproduct.exception.ProductAlreadyExistsException;
import com.ferreteria.msproduct.exception.ProductNotFoundException;
import com.ferreteria.msproduct.model.Product;
import com.ferreteria.msproduct.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    void buscarPorId_shouldReturnWhenExists() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        UUID id = UUID.randomUUID();
        Product p = entity(id, "Taladro", "Taladro profesional", new BigDecimal("89990"), "Herramientas", true);
        when(productRepository.findById(id)).thenReturn(Optional.of(p));

        ProductResponseDto result = service.buscarPorId(id);

        assertEquals(id, result.id());
        assertEquals("Taladro", result.nombre());
    }

    @Test
    void buscarPorId_shouldThrowNotFoundWhenMissing() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () -> service.buscarPorId(id));
        assertNotNull(ex.getMessage());
    }

    @Test
    void listarActivos_shouldFilterActive() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        Product active = entity(UUID.randomUUID(), "A", "", new BigDecimal("100"), "Cat", true);
        Product inactive = entity(UUID.randomUUID(), "B", "", new BigDecimal("200"), "Cat", false);
        when(productRepository.findByActivoTrue()).thenReturn(List.of(active, inactive));

        List<ProductResponseDto> result = service.listarActivos();

        assertNotNull(result);
    }

    @Test
    void buscarPorNombre_shouldReturnMatchingProducts() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        Product product = entity(UUID.randomUUID(), "Taladro", "desc", new BigDecimal("89990"), "Herramientas", true);
        when(productRepository.findByNombreContainingIgnoreCaseAndActivoTrue("tal")).thenReturn(List.of(product));

        List<ProductResponseDto> result = service.buscarPorNombre("tal");

        assertEquals(1, result.size());
        assertEquals("Taladro", result.get(0).nombre());
    }

    @Test
    void buscarPorCategoria_shouldReturnMatchingProducts() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        Product product = entity(UUID.randomUUID(), "Martillo", "desc", new BigDecimal("8990"), "Herramientas", true);
        when(productRepository.findByCategoriaAndActivoTrue("Herramientas")).thenReturn(List.of(product));

        List<ProductResponseDto> result = service.buscarPorCategoria("Herramientas");

        assertEquals(1, result.size());
        assertEquals("Martillo", result.get(0).nombre());
    }

    @Test
    void crear_shouldThrowConflictWhenExists() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        when(productRepository.existsByNombre("Taladro")).thenReturn(true);

        ProductAlreadyExistsException ex = assertThrows(ProductAlreadyExistsException.class,
                () -> service.crear(new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas")));
        assertNotNull(ex.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    void crear_shouldSaveAndReturnDto() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        when(productRepository.existsByNombre("Cinta")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product toSave = invocation.getArgument(0);
            toSave.setId(UUID.randomUUID());
            toSave.setCreatedAt(LocalDateTime.now());
            return toSave;
        });

        ProductResponseDto result = service
                .crear(new ProductRequestDto("Cinta", "desc", new BigDecimal("4990"), "Medicion"));

        assertNotNull(result);
        assertEquals("Cinta", result.nombre());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertEquals("Cinta", captor.getValue().getNombre());
    }

    @Test
    void actualizar_shouldSaveUpdatedProduct() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        UUID id = UUID.randomUUID();
        Product entity = entity(id, "Taladro", "desc", new BigDecimal("89990"), "Herramientas", true);
        when(productRepository.findById(id)).thenReturn(Optional.of(entity));
        when(productRepository.save(entity)).thenReturn(entity);

        ProductResponseDto result = service.actualizar(id,
                new ProductRequestDto("Taladro Pro", "desc nueva", new BigDecimal("99990"), "Herramientas"));

        assertEquals("Taladro Pro", result.nombre());
        assertEquals(new BigDecimal("99990"), result.precio());
        verify(productRepository).save(entity);
    }

    @Test
    void actualizar_shouldThrowNotFoundWhenMissing() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class,
                () -> service.actualizar(id,
                        new ProductRequestDto("Taladro", "desc", new BigDecimal("89990"), "Herramientas")));

        assertNotNull(ex.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    void desactivar_shouldSetActivoFalse() {
        ProductServiceImpl service = new ProductServiceImpl(productRepository);
        UUID id = UUID.randomUUID();
        Product entity = entity(id, "Taladro", "desc", new BigDecimal("89990"), "Herramientas", true);
        when(productRepository.findById(id)).thenReturn(Optional.of(entity));

        service.desactivar(id);

        verify(productRepository).save(entity);
        assertFalse(entity.getActivo());
    }

    private Product entity(UUID id, String nombre, String descripcion, BigDecimal precio, String categoria,
            boolean activo) {
        Product p = new Product();
        p.setId(id);
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setCategoria(categoria);
        p.setActivo(activo);
        p.setCreatedAt(LocalDateTime.now());
        return p;
    }
}
