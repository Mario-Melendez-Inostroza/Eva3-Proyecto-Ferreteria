package com.ferreteria.mssales.service;

import com.ferreteria.mssales.dto.*;
import java.util.List;
import java.util.UUID;

public interface VentaService {
    VentaResponseDto registrarVenta(VentaRequestDto dto);
    List<VentaResponseDto> listarTodas();
    List<VentaResponseDto> listarPorProducto(UUID productId);
    VentaResponseDto buscarPorId(UUID id);
    ResumenVentasDto obtenerResumen();
}
