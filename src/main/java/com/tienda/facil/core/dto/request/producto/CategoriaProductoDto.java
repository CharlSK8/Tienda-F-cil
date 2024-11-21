package com.tienda.facil.core.dto.request.producto;

import com.tienda.facil.core.util.enums.CategoriaProducto;
import com.tienda.facil.core.util.enums.EstadoCategoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para Categoría de Producto.
 * Esta clase se utiliza para transferir datos de categorías entre procesos.
 */
@Data
public class CategoriaProductoDto {
    /**
     * Nombre de la categoría de producto.
     * Este campo es obligatorio.
     */
    @Schema(description = "Nombre de la categoría", example = "ELECTRODOMESTICOS", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El nombre de la categoría es obligatorio")
    private CategoriaProducto categoriaProducto;

    /**
     * Descripción de la categoría.
     */
    @Schema(description = "Descripción de la categoría", example = "Categoría para productos electrónicos")
    @NotBlank(message = "El campo 'descripcion' es obligatorio")
    private String descripcion;

    /**
     * Estado de la categoría (activa o inactiva).
     */
    @Schema(description = "Estado de la categoría", example = "ACTIVA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estado de la categoría es obligatorio")
    private EstadoCategoria estadoCategoria;
}