package com.ferreteria.msinventory.service;

import com.ferreteria.msinventory.dto.*;
import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryResponseDto crear(InventoryRequestDto dto);
    InventoryResponseDto buscarPorProductId(UUID productId);
    List<InventoryResponseDto> listarTodo();
    List<InventoryResponseDto> listarStockBajo();
    InventoryResponseDto agregarStock(UUID productId, AjustarStockDto dto);
    InventoryResponseDto descontarStock(UUID productId, AjustarStockDto dto);
}
