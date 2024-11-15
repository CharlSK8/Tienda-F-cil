package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.dto.request.TestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/tienda/facil/api/v1/")
@Tag(name = "TestController", description = "Controller description")
public class TestController {

    /**
     * Controlador para manejar la solicitud de prueba.
     *
     * @param testDTO        El objeto de transferencia de datos {@link TestDTO} que contiene los datos de prueba
     *                       enviados en el cuerpo de la solicitud.
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que contiene el mensaje de bienvenida si el
     *         {@link TestDTO} es válido, o un mensaje de error si hay errores de validación.
     *
     */
    @Operation(summary = "Test Controller", description = "Devuelve un mensaje de bienvenida con el nombre proporcionado")
    @PostMapping("/test")
    public ResponseEntity<ResponseDTO<Object>> testController(@Valid @RequestBody TestDTO testDTO) {
        try {
            String responseMessage = "Welcome to tienda fácil, " + testDTO.getName();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder().response(responseMessage)
                    .code(HttpStatus.OK.value()).message("Se procesó correctamente la solicitud").build()) ;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .message("Unexpected error: " + e.getMessage()).build());
        }
    }
}
