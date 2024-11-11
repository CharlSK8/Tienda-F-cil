package com.tienda.facil.core.dto;

import com.tienda.facil.core.utils.enums.EstadoPedido;
import com.tienda.facil.core.utils.enums.MetodoPago;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) para Pedido.
 * Esta clase se utiliza para transferir datos entre procesos.
 */
@Data
public class PedidoDto {

    /**
     * ID del cliente que realiza el pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "ID del cliente que realiza el pedido", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    /**
     * ID de la prioridad del pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "ID de la prioridad del pedido", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la prioridad es obligatorio")
    private Long prioridadId;

    /**
     * Fecha en la que se realizó el pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "Fecha en la que se realizó el pedido", example = "2024-11-11T10:42:27.658Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de pedido es obligatoria")
    private Date fechaPedido;

    /**
     * Fecha de entrega estimada del pedido.
     */
    @Schema(description = "Fecha de entrega estimada del pedido", example = "2024-11-15T15:00:00")
    private Date fechaEntrega;

    /**
     * Estado actual del pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "Estado actual del pedido", example = "PENDIENTE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estado del pedido es obligatorio")
    private EstadoPedido estadoPedido;

    /**
     * Monto total del pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "Monto total del pedido", example = "150.75", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El total es obligatorio")
    private BigDecimal montoTotal;

    /**
     * Método de pago utilizado en el pedido.
     * Este campo es obligatorio.
     */
    @Schema(description = "Método de pago utilizado en el pedido", example = "TARJETA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    /**
     * Dirección de envío para el pedido.
     */
    @Schema(description = "Dirección de envío para el pedido", example = "123 Calle Ejemplo, Ciudad")
    private String direccionEnvio;

    /**
     * Número de seguimiento del pedido.
     */
    @Schema(description = "Número de seguimiento del pedido", example = "ABC123456789")
    private String numeroSeguimiento;
}