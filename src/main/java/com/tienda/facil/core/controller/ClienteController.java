package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.request.ActualizarClienteRequestDTO;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.service.IClienteService;
import com.tienda.facil.core.util.constants.Constants;
import com.tienda.facil.core.util.enums.EstadoActivo;
import com.tienda.facil.core.util.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes")
@RequestMapping("/tienda/facil/api/v1/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteRepository clienteRepository;
    private final IClienteService clienteService;

    @Operation(summary = "Reporte clientes activos", description = "Genera un reporte PDF de todos los clientes los cuales se encuentran en estado ACTIVO.")
    @GetMapping("/reporte-activos")
    public ResponseEntity<byte[]> generateActiveClientsReport() {
        logger.info("Iniciando generación de reporte de clientes activos");

        // Obtener clientes activos
        List<Cliente> activeClients = clienteRepository.findByActivo(EstadoActivo.ACTIVO);

        if (activeClients.isEmpty()) {
            logger.warn("No se encontraron clientes activos para generar el reporte");
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "No hay clientes activos para generar el reporte"
            );
        }

        try {
            logger.debug("Generando reporte para {} clientes activos", activeClients.size());

            // Generar el reporte
            byte[] reportBytes = clienteService.exportClientReport(
                    activeClients,
                    "reports/clientesActivos.jrxml"
            );

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "clientes_activos.pdf");

            logger.info("Reporte generado exitosamente");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        } catch (Exception e) {
            logger.error("Error al generar el reporte de clientes activos: {}", e.getMessage(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte: " + e.getMessage()
            );
        }
    }

    /**
     * Actualiza los datos de un cliente existente en el sistema.
     *
     * Este método permite actualizar los campos de un cliente, como nombre, apellido, email,
     * contacto, entre otros. Solo los campos proporcionados en el DTO serán actualizados.
     * Se utiliza JWT para la autenticación y autorización del usuario que realiza la solicitud.
     *
     * @param actualizarClienteRequestDTO El DTO que contiene los nuevos datos del cliente.
     * @param id El ID único del cliente que se desea actualizar.
     * @return Un objeto ResponseEntity que contiene la respuesta del servicio, incluyendo un
     *         objeto ResponseDTO con el resultado de la operación.
     */
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos del cliente, incluyendo nombre, contacto, email, etc.")
    @PostMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDTO> actualizarCliente(
            @Valid @RequestBody ActualizarClienteRequestDTO actualizarClienteRequestDTO, @PathVariable Long id){
        try{
            Result<String, String> result = clienteService.actualizarCliente(actualizarClienteRequestDTO, id);
            return result.isSuccess()
                    ? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                    .message(Constants.MESSAGE_OK)
                    .code(HttpStatus.OK.value()).response(result.getValue()).build())
                    : ResponseEntity.status(result.getStatusCode())
                    .body(ResponseDTO.<String>builder()
                            .message(Constants.MESSAGE_ERROR)
                            .code(result.getStatusCode()).response(String.join("\n", result.getErrors())).build());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .message(Constants.MESSAGE_ERROR)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .response(e.getMessage())
                    .build());
        }
    }
}