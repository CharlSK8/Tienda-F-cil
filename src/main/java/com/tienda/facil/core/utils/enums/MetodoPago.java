package com.tienda.facil.core.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que representa los métodos de pago disponibles.
 */
public enum MetodoPago {
    TARJETA,  // Pago con tarjeta de crédito o débito
    EFECTIVO,  // Pago en efectivo
    PAYPAL,  // Pago a través de PayPal
    BIZUM;  // Pago a través de Bizum

    /**
     * Crea una instancia de MetodoPago a partir de una cadena.
     *
     * @param value el valor de la cadena
     * @return la instancia de MetodoPago correspondiente
     */
    @JsonCreator
    public static MetodoPago fromValue(String value) {
        return MetodoPago.valueOf(value.toUpperCase());
    }

    /**
     * Convierte la instancia de MetodoPago a su representación en cadena.
     *
     * @return el nombre del método de pago
     */
    @JsonValue
    public String toValue() {
        return name();
    }
}