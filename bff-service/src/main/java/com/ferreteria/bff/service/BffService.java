package com.ferreteria.bff.service;

import com.ferreteria.bff.client.*;
import com.ferreteria.bff.dto.*;
import com.ferreteria.bff.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BffService {

    private final AuthClient authClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final SalesClient salesClient;
    private final NotificationClient notificationClient;

    // ----------------------------------------------------------
    // AUTENTICACIÓN (públicas, sin token)
    // ----------------------------------------------------------
    public MessageResponseDto register(RegisterRequestDto dto) {
        return authClient.register(dto);
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        return authClient.login(dto);
    }

    // ----------------------------------------------------------
    // PRODUCTOS (requieren token)
    // ----------------------------------------------------------
    public List<ProductResponseDto> listarProductos(String authHeader) {
        validar(authHeader);
        return productClient.listar();
    }

    public ProductResponseDto buscarProducto(String authHeader, UUID id) {
        validar(authHeader);
        return productClient.buscarPorId(id);
    }

    public List<ProductResponseDto> buscarProductosPorNombre(String authHeader, String nombre) {
        validar(authHeader);
        return productClient.buscarPorNombre(nombre);
    }

    public List<ProductResponseDto> buscarProductosPorCategoria(String authHeader, String categoria) {
        validar(authHeader);
        return productClient.buscarPorCategoria(categoria);
    }

    public ProductResponseDto crearProducto(String authHeader, ProductRequestDto dto) {
        validar(authHeader);
        return productClient.crear(dto);
    }

    public ProductResponseDto actualizarProducto(String authHeader, UUID id, ProductRequestDto dto) {
        validar(authHeader);
        return productClient.actualizar(id, dto);
    }

    public MessageResponseDto desactivarProducto(String authHeader, UUID id) {
        validar(authHeader);
        return productClient.desactivar(id);
    }

    // ----------------------------------------------------------
    // INVENTARIO (requieren token)
    // ----------------------------------------------------------
    public InventoryResponseDto crearInventario(String authHeader, InventoryRequestDto dto) {
        validar(authHeader);
        return inventoryClient.crear(dto);
    }

    public List<InventoryResponseDto> listarInventario(String authHeader) {
        validar(authHeader);
        return inventoryClient.listar();
    }

    public List<InventoryResponseDto> stockBajo(String authHeader) {
        validar(authHeader);
        return inventoryClient.stockBajo();
    }

    public InventoryResponseDto buscarInventarioPorProducto(String authHeader, UUID productId) {
        validar(authHeader);
        return inventoryClient.buscarPorProducto(productId);
    }

    public InventoryResponseDto agregarStock(String authHeader, UUID productId, AjustarStockDto dto) {
        validar(authHeader);
        return inventoryClient.agregarStock(productId, dto);
    }

    public InventoryResponseDto descontarStock(String authHeader, UUID productId, AjustarStockDto dto) {
        validar(authHeader);
        return inventoryClient.descontarStock(productId, dto);
    }

    // ----------------------------------------------------------
    // VENTAS (requieren token)
    // ----------------------------------------------------------
    public VentaResponseDto registrarVenta(String authHeader, VentaRequestDto dto) {
        validar(authHeader);
        return salesClient.registrar(dto);
    }

    public List<VentaResponseDto> listarVentas(String authHeader) {
        validar(authHeader);
        return salesClient.listar();
    }

    public VentaResponseDto buscarVenta(String authHeader, UUID id) {
        validar(authHeader);
        return salesClient.buscarPorId(id);
    }

    public List<VentaResponseDto> ventasPorProducto(String authHeader, UUID productId) {
        validar(authHeader);
        return salesClient.listarPorProducto(productId);
    }

    public ResumenVentasDto resumenVentas(String authHeader) {
        validar(authHeader);
        return salesClient.resumen();
    }

    // ----------------------------------------------------------
    // NOTIFICACIONES (requieren token)
    // ----------------------------------------------------------
    public List<NotificationResponseDto> listarNotificaciones(String authHeader) {
        validar(authHeader);
        return notificationClient.listarTodas();
    }

    public List<NotificationResponseDto> notificacionesNoLeidas(String authHeader) {
        validar(authHeader);
        return notificationClient.listarNoLeidas();
    }

    public NotificationResponseDto marcarLeida(String authHeader, UUID id) {
        validar(authHeader);
        return notificationClient.marcarLeida(id);
    }

    public MessageResponseDto marcarTodasLeidas(String authHeader) {
        validar(authHeader);
        return notificationClient.marcarTodasLeidas();
    }

    // ----------------------------------------------------------
    // validar() — extrae token del header y consulta ms-auth
    // ----------------------------------------------------------
    private void validar(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token no proporcionado");
        }
        String token = authHeader.substring(7);
        ValidateTokenResponseDto result = authClient.validateToken(token);
        if (!result.valid()) {
            throw new UnauthorizedException("Token inválido o expirado");
        }
    }
}
