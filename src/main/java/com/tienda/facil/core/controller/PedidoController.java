package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.PedidoDto;
import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de pedidos.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tienda/facil/api/v1/pedidos")
@Tag(name = "PedidoController", description = "Gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Constructor de PedidoController.
     *
     * @param pedidoService El servicio de pedidos.
     */
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Endpoint para crear un nuevo pedido.
     *
     * @param pedidoDto     El objeto {@link PedidoDto} que contiene los datos del pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que contiene el pedido creado o un mensaje de error.
     */
    @Operation(summary = "Crear Pedido", description = "Crea un nuevo pedido en la base de datos")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> crearPedido(@Valid @RequestBody PedidoDto pedidoDto, BindingResult bindingResult) {
        // Llamar al servicio para crear el pedido y obtener el ResponseDTO
        ResponseDTO response = pedidoService.crearPedido(pedidoDto, bindingResult);

        // Devolver la respuesta del servicio
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Endpoint para obtener todos los pedidos.
     *
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que contiene la lista de pedidos.
     */
    @Operation(summary = "Obtener todos los pedidos", description = "Obtiene todos los pedidos de la base de datos")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obtener pedido exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<ResponseDTO> obtenerPedidos() {
        ResponseDTO response = pedidoService.obtenerPedidos();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Endpoint para obtener un pedido por su ID.
     *
     * @param id El ID del pedido a obtener.
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que contiene el pedido obtenido o un mensaje de error.
     */
    @Operation(summary = "Obtener Pedido por ID", description = "Obtiene un pedido por su ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obtener pedido por ID exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtenerPedidoById(@PathVariable Long id) {
        ResponseDTO response = pedidoService.obtenerPedido(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Endpoint para eliminar un pedido por su ID.
     *
     * @param id El ID del pedido a eliminar.
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que indica el éxito o error de la operación.
     */
    @Operation(summary = "Eliminar Pedido", description = "Elimina un pedido de la base de datos por su ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Eliminar pedido exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarPedido(@PathVariable Long id) {
        ResponseDTO response = pedidoService.eliminarPedido(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Endpoint para actualizar un pedido por su ID.
     *
     * @param id            El ID del pedido a actualizar.
     * @param pedidoDto     Los nuevos datos del pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseEntity} con un {@link ResponseDTO} que contiene el pedido actualizado o un mensaje de error.
     */
    @Operation(summary = "Actualizar Pedido", description = "Actualiza un pedido existente en la base de datos")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actualizar pedido exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoDto pedidoDto,
            BindingResult bindingResult) {
        ResponseDTO response = pedidoService.actualizarPedido(id, pedidoDto, bindingResult);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}