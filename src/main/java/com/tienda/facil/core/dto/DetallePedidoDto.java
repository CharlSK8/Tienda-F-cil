package com.tienda.facil.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) para la entidad DetallePedido.
 */
@Data
public class DetallePedidoDto {

    /**
     * ID del pedido asociado.
     */
    @Schema(description = "ID del pedido asociado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    /**
     * ID del producto asociado.
     */
    @Schema(description = "ID del producto asociado", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    /**
     * Cantidad del producto.
     */
    @Schema(description = "Cantidad del producto", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    /**
     * Subtotal calculado.
     */
    @Schema(description = "Subtotal calculado", example = "150.00")
    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private BigDecimal subtotal;
}