package com.tienda.facil.core.exception;

import com.tienda.facil.core.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja excepciones de validación para los campos del DTO.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseDTO.builder()
                .response(errors)
                .message("Error en la validación de campos")
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    /**
     * Maneja excepciones de errores de formato JSON, como enums inválidos.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Error en el formato del JSON: " + ex.getMostSpecificCause().getMessage();

        return ResponseDTO.builder()
                .response(errorMessage)
                .message("Error en el formato de datos")
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}