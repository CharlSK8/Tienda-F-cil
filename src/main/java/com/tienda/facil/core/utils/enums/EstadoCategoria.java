package com.tienda.facil.core.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoCategoria {
    ACTIVA,
    INACTIVA;

    // Método de serialización, convierte el valor del enum a su representación en texto.
    @JsonValue
    public String toJson() {
        return name();
    }

    // Método de deserialización, acepta cadenas en JSON y convierte al valor del enum correspondiente.
    @JsonCreator
    public static EstadoCategoria fromJson(String value) {
        try {
            return EstadoCategoria.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor no válido para EstadoCategoria: " + value);
        }
    }
}