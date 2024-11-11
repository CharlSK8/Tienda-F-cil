package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.PedidoDto;
import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping
    public ResponseEntity<ResponseDTO> crearPedido(@Valid @RequestBody PedidoDto pedidoDto, BindingResult bindingResult) {
        // Llamar al servicio para crear el pedido y obtener el ResponseDTO
        ResponseDTO response = pedidoService.crearPedido(pedidoDto, bindingResult);

        // Devolver la respuesta del servicio
        return ResponseEntity.status(response.getCode()).body(response);

    }
}