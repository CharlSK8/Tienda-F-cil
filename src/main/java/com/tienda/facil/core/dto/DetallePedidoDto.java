package com.tienda.facil.core.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePedidoDto {
    private Long id;
    private Long pedidoId;    // ID del pedido asociado
    private Long productoId;  // ID del producto asociado
    private int cantidad;
    private BigDecimal subtotal;
}