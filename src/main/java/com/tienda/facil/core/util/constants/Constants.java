package com.tienda.facil.core.util.constants;

/**
 * Esta clase permite definir todas aquellas constantes que se requieran en la lógica
 * @author HcharlinsonPerez
 */
public final class Constants {

    //Controller message
    public static final String MESSAGE_OK = "La solicitud se procesó correctamente.";
    public static final String MESSAGE_ERROR = "Hubo un error interno al procesar la solicitud.";
    public static final String MESSAGE_ERROR_BODY = "Se detectaron errores en el cuerpo de la solicitud.";

    private Constants() {
        throw new IllegalStateException("Constants class");
    }
}
