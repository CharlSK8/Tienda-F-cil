package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.DetallePedidoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controlador REST para gestionar los detalles de los pedidos.
 */
@RestController
@RequestMapping("/api/detalles-pedido")
public class DetalltePedidoController {
    private final DetallePedidoService detallePedidoService;

    /**
     * Constructor del controlador DetalltePedidoController.
     *
     * @param detallePedidoService Servicio para gestionar los detalles de los pedidos.
     */
    public DetalltePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    /**
     * Crea un nuevo detalle para un pedido.
     *
     * @param detalleDto DTO con la información del detalle de pedido.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Crear Detalle de Pedido", description = "Crea un nuevo detalle para un pedido")
    @PostMapping
    public ResponseEntity<ResponseDTO> crearDetallePedido(@Valid @RequestBody DetallePedidoDto detalleDto) {
        ResponseDTO<Object> response = detallePedidoService.crearDetallePedido(detalleDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Obtiene los detalles activos de un pedido específico.
     *
     * @param pedidoId ID del pedido.
     * @return ResponseEntity con la lista de detalles de pedido.
     */
    @Operation(summary = "Obtener Detalles por Pedido", description = "Obtiene los detalles activos de un pedido específico")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<ResponseDTO> obtenerDetallesPorPedido(@PathVariable Long pedidoId) {
        ResponseDTO<Object> response = detallePedidoService.obtenerDetallesPorPedido(pedidoId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Marca un detalle de pedido como inactivo.
     *
     * @param id ID del detalle de pedido.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Eliminar Detalle de Pedido", description = "Marca un detalle de pedido como inactivo")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarDetallePedido(@PathVariable Long id) {
        ResponseDTO<Object> response = detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}