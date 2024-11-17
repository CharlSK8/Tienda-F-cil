package com.tienda.facil.core.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que representa la prioridad de un pedido.
 */
public enum PrioridadPedido {
    ALTA,
    MEDIA,
    BAJA;

    /**
     * Crea una instancia de PrioridadPedido a partir de una cadena.
     *
     * @param value el valor de la cadena
     * @return la instancia de PrioridadPedido correspondiente
     */
    @JsonCreator
    public static PrioridadPedido fromString(String value) {
        return PrioridadPedido.valueOf(value.toUpperCase());
    }

    /**
     * Convierte la instancia de PrioridadPedido a su representación en cadena.
     *
     * @return el nombre de la prioridad
     */
    @JsonValue
    public String toValue() {
        return name();
    }
}