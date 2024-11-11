package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.PedidoDto;
import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.model.ClienteModel;
import com.tienda.facil.core.model.PedidoModel;
import com.tienda.facil.core.model.PrioridadModel;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.repository.PedidoRepository;
import com.tienda.facil.core.repository.PrioridadRepository;
import com.tienda.facil.core.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Servicio para la gestión de pedidos.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PrioridadRepository prioridadRepository;

    /**
     * Constructor de PedidoService.
     *
     * @param pedidoRepository    Repositorio de pedidos.
     * @param clienteRepository   Repositorio de clientes.
     * @param prioridadRepository Repositorio de prioridades.
     */
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
                    .collect(joining(", "));

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

    /**
     * Convierte un {@link PedidoDto} a un {@link PedidoModel}.
     *
     * @param pedidoDto El objeto {@link PedidoDto} que contiene los datos del pedido.
     * @param cliente   El objeto {@link ClienteModel} asociado al pedido.
     * @param prioridad El objeto {@link PrioridadModel} asociado al pedido.
     * @return El objeto {@link PedidoModel} mapeado.
     */
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

    /**
     * Actualiza un pedido en la base de datos.
     *
     * @param id            El ID del pedido a actualizar.
     * @param pedidoDto     Los nuevos datos para el pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseDTO} que contiene el pedido actualizado o un mensaje de error.
     */
    public ResponseDTO actualizarPedido(Long id, @Valid PedidoDto pedidoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        Optional<PedidoModel> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            PedidoModel pedidoModel = pedidoOpt.get();

            // Validar y asignar cliente
            ClienteModel cliente = clienteRepository.findById(pedidoDto.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Validar y asignar prioridad
            PrioridadModel prioridad = prioridadRepository.findById(pedidoDto.getPrioridadId())
                    .orElseThrow(() -> new IllegalArgumentException("Prioridad no encontrada"));

            // Actualizar los campos del pedido
            pedidoModel.setCliente(cliente);
            pedidoModel.setPrioridad(prioridad);
            pedidoModel.setFechaPedido(pedidoDto.getFechaPedido());
            pedidoModel.setFechaEntrega(pedidoDto.getFechaEntrega());
            pedidoModel.setEstadoPedido(pedidoDto.getEstadoPedido());
            pedidoModel.setFechaModificacion(new Date());
            pedidoModel.setMontoTotal(pedidoDto.getMontoTotal());
            pedidoModel.setMetodoPago(pedidoDto.getMetodoPago());
            pedidoModel.setDireccionEnvio(pedidoDto.getDireccionEnvio());
            pedidoModel.setNumeroSeguimiento(pedidoDto.getNumeroSeguimiento());

            // Guardar los cambios en el repositorio
            PedidoModel pedidoActualizado = pedidoRepository.save(pedidoModel);

            return ResponseDTO.builder()
                    .response(pedidoActualizado)
                    .code(HttpStatus.OK.value())
                    .message("Pedido actualizado exitosamente")
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constantes.PEDIDO_NO_ENCONTRADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    /**
     * Obtiene todos los pedidos de la base de datos.
     *
     * @return {@link ResponseDTO} con la lista de pedidos obtenidos.
     */
    public ResponseDTO obtenerPedidos() {
        List<PedidoDto> pedidos = pedidoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.<PedidoDto>toList());

        return ResponseDTO.builder()
                .response(pedidos)
                .code(HttpStatus.OK.value())
                .message(Constantes.PEDIDOS_OBTENIDOS)
                .build();
    }

    /**
     * Obtiene un pedido por su ID.
     *
     * @param id El ID del pedido a obtener.
     * @return {@link ResponseDTO} con el pedido obtenido o un mensaje de error.
     */
    public ResponseDTO obtenerPedido(Long id) {
        Optional<PedidoModel> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            PedidoDto pedidoDto = convertToDto(pedidoOpt.get());
            return ResponseDTO.builder()
                    .response(pedidoDto)
                    .code(HttpStatus.OK.value())
                    .message("Pedido obtenido exitosamente")
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constantes.PEDIDO_NO_ENCONTRADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    /**
     * Convierte un {@link PedidoModel} a un {@link PedidoDto}.
     *
     * @param pedidoModel El objeto {@link PedidoModel} a convertir.
     * @return El objeto {@link PedidoDto} convertido.
     */
    private PedidoDto convertToDto(PedidoModel pedidoModel) {
        PedidoDto dto = new PedidoDto();
        dto.setClienteId(pedidoModel.getCliente().getId());
        dto.setPrioridadId(pedidoModel.getPrioridad().getId());
        dto.setFechaPedido(pedidoModel.getFechaPedido());
        dto.setFechaEntrega(pedidoModel.getFechaEntrega());
        dto.setEstadoPedido(pedidoModel.getEstadoPedido());
        dto.setMontoTotal(pedidoModel.getMontoTotal());
        dto.setMetodoPago(pedidoModel.getMetodoPago());
        dto.setDireccionEnvio(pedidoModel.getDireccionEnvio());
        dto.setNumeroSeguimiento(pedidoModel.getNumeroSeguimiento());
        return dto;
    }

    /**
     * Elimina un pedido de la base de datos por su ID.
     *
     * @param id El ID del pedido a eliminar.
     * @return {@link ResponseDTO} que indica si la eliminación fue exitosa o si ocurrió un error.
     */
    public ResponseDTO eliminarPedido(Long id) {
        Optional<PedidoModel> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            pedidoRepository.deleteById(id);
            return ResponseDTO.builder()
                    .message("Pedido eliminado exitosamente")
                    .code(HttpStatus.OK.value())
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message("Pedido no encontrado")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

}