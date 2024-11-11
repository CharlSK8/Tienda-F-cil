package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.PedidoDto;
import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.model.ClienteModel;
import com.tienda.facil.core.model.PedidoModel;
import com.tienda.facil.core.model.PrioridadModel;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.repository.PedidoRepository;
import com.tienda.facil.core.repository.PrioridadRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

/**
 * Servicio para la gestión de pedidos.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ClienteRepository clienteRepository;

    private final PrioridadRepository prioridadRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, PrioridadRepository prioridadRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.prioridadRepository = prioridadRepository;
    }

    /**
     * Crea un nuevo pedido en la base de datos.
     *
     * @param pedidoDto     El objeto {@link PedidoDto} que contiene los datos del pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseDTO} con el pedido creado o un mensaje de error.
     */
    public ResponseDTO crearPedido(@Valid PedidoDto pedidoDto, BindingResult bindingResult) {
        // Validar datos del DTO
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        try {
            // Validar cliente
            ClienteModel cliente = clienteRepository.findById(pedidoDto.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Validar prioridad
            PrioridadModel prioridad = prioridadRepository.findById(pedidoDto.getPrioridadId())
                    .orElseThrow(() -> new IllegalArgumentException("Prioridad no encontrada"));

            // Mapear PedidoDto a PedidoModel
            PedidoModel pedidoModel = getPedidoModel(pedidoDto, cliente, prioridad);

            // Guardar el pedido en la base de datos llamando al Repository
            PedidoModel nuevoPedido = pedidoRepository.save(pedidoModel);

            // Construir y devolver respuesta exitosa
            return ResponseDTO.builder()
                    .response(nuevoPedido)
                    .code(HttpStatus.CREATED.value())
                    .message("Pedido creado exitosamente ")
                    .build();

        } catch (IllegalArgumentException e) {
            // Manejo de error en caso de cliente o prioridad no encontrados
            return ResponseDTO.builder()
                    .message(e.getMessage())
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseDTO.builder()
                    .message("Error inesperado: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    private static PedidoModel getPedidoModel(PedidoDto pedidoDto, ClienteModel cliente, PrioridadModel prioridad) {
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setCliente(cliente);
        pedidoModel.setPrioridad(prioridad);
        pedidoModel.setFechaPedido(pedidoDto.getFechaPedido());
        pedidoModel.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedidoModel.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedidoModel.setMontoTotal(pedidoDto.getMontoTotal());
        pedidoModel.setMetodoPago(pedidoDto.getMetodoPago());
        pedidoModel.setDireccionEnvio(pedidoDto.getDireccionEnvio());
        pedidoModel.setNumeroSeguimiento(pedidoDto.getNumeroSeguimiento());
        return pedidoModel;
    }
}