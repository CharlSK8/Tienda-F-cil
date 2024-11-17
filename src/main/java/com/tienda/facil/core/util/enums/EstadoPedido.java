package com.tienda.facil.core.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que representa el estado de un pedido.
 */
public enum EstadoPedido {
    PENDIENTE,  // Pedido pendiente
    EN_PROCESO,  // Pedido en proceso
    ENTREGADO,  // Pedido entregado
    CANCELADO;  // Pedido cancelado

    /**
     * Crea una instancia de EstadoPedido a partir de una cadena.
     *
     * @param value el valor de la cadena
     * @return la instancia de EstadoPedido correspondiente
     */
    @JsonCreator
    public static EstadoPedido fromValue(String value) {
        return EstadoPedido.valueOf(value.toUpperCase());
    }

    /**
     * Convierte la instancia de EstadoPedido a su representación en cadena.
     *
     * @return el nombre del estado del pedido
     */
    @JsonValue
    public String toValue() {
        return name();
    }
}