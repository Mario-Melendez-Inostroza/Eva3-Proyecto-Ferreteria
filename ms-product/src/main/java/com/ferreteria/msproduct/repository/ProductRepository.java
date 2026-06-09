package com.ferreteria.msproduct.repository;

import com.ferreteria.msproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByActivoTrue();
    List<Product> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    List<Product> findByCategoriaAndActivoTrue(String categoria);
    boolean existsByNombre(String nombre);
}
