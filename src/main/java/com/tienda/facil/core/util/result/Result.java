package com.tienda.facil.core.util.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un resultado de una operación que puede ser exitosa o fallida.
 * Esta clase se utiliza para encapsular los resultados de una operación, incluyendo
 * el valor de retorno en caso de éxito, una lista de errores en caso de fallo,
 * y el código de estado HTTP que refleja el resultado de la operación.
 *
 * @param <R> el tipo del valor de retorno en caso de éxito.
 * @param <T> el tipo de los errores en caso de fallo.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<R, T> {

    private  R value;
    private boolean isSuccess;
    private List<T> errors;
    private int statusCode;

    /**
     * Crea un resultado exitoso con un valor de retorno.
     * Este método se utiliza para crear una instancia de {@link Result} que
     * representa una operación exitosa, con un valor de retorno y un estado HTTP
     * de éxito (200 OK).
     *
     * @param value el valor de retorno en caso de éxito.
     * @return una nueva instancia de {@link Result} que indica el éxito de la operación.
     */
    public Result<R, T> success(R value) {
        return new Result<>(value, true, new ArrayList<T>(), HttpStatus.OK.value());
    }

    /**
     * Crea un resultado fallido con una lista de errores y un código de estado HTTP.
     *
     * Este método se utiliza para crear una instancia de {@link Result} que
     * representa una operación fallida, con una lista de errores y un código de
     * estado HTTP que refleja el tipo de error ocurrido.
     *
     * @param errors la lista de errores que describe la razón del fallo.
     * @param statusCode el código de estado HTTP que refleja el fallo.
     * @return una nueva instancia de {@link Result} que indica el fallo de la operación.
     */
    public Result<R, T> fail(List<T> errors, int statusCode) {
        return new Result<>(null, false, errors, statusCode);
    }

}