package com.tienda.facil.core.dto.producto;

import lombok.Data;

@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioUnitario;
    private int stock;
    private Long categoriaId;
}