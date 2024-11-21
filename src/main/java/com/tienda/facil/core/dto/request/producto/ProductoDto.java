package com.tienda.facil.core.dto.request.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) para la entidad Producto.
 */
@Data
public class ProductoDto {

    /**
     * ID del producto (solo necesario para actualizaciones).
     */
    @Schema(description = "ID del producto (solo necesario para actualizaciones)", example = "1")
    private Long id; // para actualizaciones de producto

    /**
     * Nombre del producto.
     */
    @Schema(description = "Nombre del producto", example = "Laptop Dell", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    /**
     * Descripción del producto.
     */
    @Schema(description = "Descripción del producto", example = "Laptop con 16GB RAM y 512GB SSD")
    private String descripcion;

    /**
     * Precio unitario del producto.
     */
    @Schema(description = "Precio unitario del producto", example = "750.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private BigDecimal precioUnitario;

    /**
     * Cantidad en stock del producto.
     */
    @Schema(description = "Cantidad en stock del producto", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    /**
     * ID de la categoría del producto.
     */
    @Schema(description = "ID de la categoría del producto", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;
}