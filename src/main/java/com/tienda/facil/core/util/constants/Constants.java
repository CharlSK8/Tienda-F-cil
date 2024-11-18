package com.tienda.facil.core.util.constants;

/**
 * Esta clase permite definir todas aquellas constantes que se requieran en la lógica
 *
 * @author HcharlinsonPerez
 */
public final class Constants {

    //Controller message
    public static final String MESSAGE_OK = "La solicitud se procesó correctamente.";
    public static final String MESSAGE_ERROR = "Hubo un error interno al procesar la solicitud.";
    public static final String MESSAGE_ERROR_BODY = "Se detectaron errores en el cuerpo de la solicitud.";

    public static final String PEDIDO_NO_ENCONTRADO = "Pedido no encontrado";
    public static final String PEDIDOS_OBTENIDOS = "Pedidos obtenidos exitosamente";
    public static final String PRODUCTO_ELIMINADO_O_ELIMINADO = "Producto no encontrado o ya eliminado";

    private Constants() {
        throw new IllegalStateException("Constants class");
    }
}