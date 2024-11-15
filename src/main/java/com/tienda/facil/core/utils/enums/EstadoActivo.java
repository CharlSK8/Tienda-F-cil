package com.tienda.facil.core.utils.enums;

/**
 * Enumeración que representa el estado de actividad.
 */
public enum EstadoActivo {
    ACTIVO,  // Estado activo
    INACTIVO;  // Estado inactivo

    /**
     * Crea una instancia de EstadoActivo a partir de una cadena.
     *
     * @param value el valor de la cadena
     * @return la instancia de EstadoActivo correspondiente
     */
    public static EstadoActivo fromValue(String value) {
        return EstadoActivo.valueOf(value.toUpperCase());
    }

    /**
     * Convierte la instancia de EstadoActivo a su representación en cadena.
     *
     * @return el nombre del estado de actividad
     */
    public String toValue() {
        return name();
    }
}