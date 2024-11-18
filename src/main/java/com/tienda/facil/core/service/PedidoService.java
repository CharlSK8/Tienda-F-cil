package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.request.PedidoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.model.Pedido;
import com.tienda.facil.core.model.Prioridad;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.repository.PedidoRepository;
import com.tienda.facil.core.repository.PrioridadRepository;
import com.tienda.facil.core.util.constants.Constants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PrioridadRepository prioridadRepository;

    /**
     * Crea un nuevo pedido en la base de datos.
     *
     * @param pedidoDto     El objeto {@link PedidoDto} que contiene los datos del pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseDTO} con el pedido creado o un mensaje de error.
     */
    public ResponseDTO<Object>  crearPedido(@Valid PedidoDto pedidoDto, BindingResult bindingResult) {
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
            Cliente cliente = clienteRepository.findById(pedidoDto.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Validar prioridad
            Prioridad prioridad = prioridadRepository.findById(pedidoDto.getPrioridadId())
                    .orElseThrow(() -> new IllegalArgumentException("Prioridad no encontrada"));

            // Mapear PedidoDto a PedidoModel
            Pedido pedido = getPedidoModel(pedidoDto, cliente, prioridad);

            // Guardar el pedido en la base de datos llamando al Repository
            Pedido nuevoPedido = pedidoRepository.save(pedido);

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
     * Convierte un {@link PedidoDto} a un {@link Pedido}.
     *
     * @param pedidoDto El objeto {@link PedidoDto} que contiene los datos del pedido.
     * @param cliente   El objeto {@link Cliente} asociado al pedido.
     * @param prioridad El objeto {@link Prioridad} asociado al pedido.
     * @return El objeto {@link Pedido} mapeado.
     */
    private static Pedido getPedidoModel(PedidoDto pedidoDto, Cliente cliente, Prioridad prioridad) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPrioridad(prioridad);
        pedido.setFechaPedido(pedidoDto.getFechaPedido());
        pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedido.setMontoTotal(pedidoDto.getMontoTotal());
        pedido.setMetodoPago(pedidoDto.getMetodoPago());
        pedido.setDireccionEnvio(pedidoDto.getDireccionEnvio());
        pedido.setNumeroSeguimiento(pedidoDto.getNumeroSeguimiento());
        return pedido;
    }

    /**
     * Actualiza un pedido en la base de datos.
     *
     * @param id            El ID del pedido a actualizar.
     * @param pedidoDto     Los nuevos datos para el pedido.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseDTO} que contiene el pedido actualizado o un mensaje de error.
     */
    public ResponseDTO<Object> actualizarPedido(Long id, @Valid PedidoDto pedidoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();

            // Validar y asignar cliente
            Cliente cliente = clienteRepository.findById(pedidoDto.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Validar y asignar prioridad
            Prioridad prioridad = prioridadRepository.findById(pedidoDto.getPrioridadId())
                    .orElseThrow(() -> new IllegalArgumentException("Prioridad no encontrada"));

            // Actualizar los campos del pedido
            pedido.setCliente(cliente);
            pedido.setPrioridad(prioridad);
            pedido.setFechaPedido(pedidoDto.getFechaPedido());
            pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
            pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
            pedido.setFechaModificacion(new Date());
            pedido.setMontoTotal(pedidoDto.getMontoTotal());
            pedido.setMetodoPago(pedidoDto.getMetodoPago());
            pedido.setDireccionEnvio(pedidoDto.getDireccionEnvio());
            pedido.setNumeroSeguimiento(pedidoDto.getNumeroSeguimiento());

            // Guardar los cambios en el repositorio
            Pedido pedidoActualizado = pedidoRepository.save(pedido);

            return ResponseDTO.builder()
                    .response(pedidoActualizado)
                    .code(HttpStatus.OK.value())
                    .message("Pedido actualizado exitosamente")
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constants.PEDIDO_NO_ENCONTRADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    /**
     * Obtiene todos los pedidos de la base de datos.
     *
     * @return {@link ResponseDTO} con la lista de pedidos obtenidos.
     */
    public ResponseDTO<Object> obtenerPedidos() {
        List<PedidoDto> pedidos = pedidoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.<PedidoDto>toList());

        return ResponseDTO.builder()
                .response(pedidos)
                .code(HttpStatus.OK.value())
                .message(Constants.PEDIDOS_OBTENIDOS)
                .build();
    }

    /**
     * Obtiene un pedido por su ID.
     *
     * @param id El ID del pedido a obtener.
     * @return {@link ResponseDTO} con el pedido obtenido o un mensaje de error.
     */
    public ResponseDTO<Object> obtenerPedido(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            PedidoDto pedidoDto = convertToDto(pedidoOpt.get());
            return ResponseDTO.builder()
                    .response(pedidoDto)
                    .code(HttpStatus.OK.value())
                    .message("Pedido obtenido exitosamente")
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constants.PEDIDO_NO_ENCONTRADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    /**
     * Convierte un {@link Pedido} a un {@link PedidoDto}.
     *
     * @param pedido El objeto {@link Pedido} a convertir.
     * @return El objeto {@link PedidoDto} convertido.
     */
    private PedidoDto convertToDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setClienteId(pedido.getCliente().getId());
        dto.setPrioridadId(pedido.getPrioridad().getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaEntrega(pedido.getFechaEntrega());
        dto.setEstadoPedido(pedido.getEstadoPedido());
        dto.setMontoTotal(pedido.getMontoTotal());
        dto.setMetodoPago(pedido.getMetodoPago());
        dto.setDireccionEnvio(pedido.getDireccionEnvio());
        dto.setNumeroSeguimiento(pedido.getNumeroSeguimiento());
        return dto;
    }

    /**
     * Marca un pedido como inactivo (eliminado lógicamente).
     *
     * @param id El ID del pedido a marcar como inactivo.
     * @return {@link ResponseDTO} que indica el éxito o error de la operación.
     */
    public ResponseDTO<Object> eliminarPedido(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findByIdAndActivoTrue(id);

        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setActivo(false); // Cambio al estado inactivo
            pedido.setFechaModificacion(new Date());
            pedidoRepository.save(pedido);

            return ResponseDTO.builder()
                    .message("Pedido eliminado lógicamente")
                    .code(HttpStatus.OK.value())
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message("Pedido no encontrado o ya eliminado")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    /**
     * Busca un pedido por su número de seguimiento.
     *
     * @param pedidoDto     El objeto {@link PedidoDto} que contiene el número de seguimiento.
     * @param bindingResult Resultado de la validación del {@link PedidoDto}.
     * @return {@link ResponseDTO} con el pedido encontrado o un mensaje de error.
     */
    public ResponseDTO<Object> buscarPedidoPorNumero(@Valid PedidoDto pedidoDto, BindingResult bindingResult) {
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
            // Buscar el pedido por número de seguimiento
            Optional<Pedido> pedidoOpt = pedidoRepository.findByNumeroSeguimiento(pedidoDto.getNumeroSeguimiento());

            if (pedidoOpt.isPresent()) {
                PedidoDto pedidoDtoRespuesta = convertToDto(pedidoOpt.get());
                return ResponseDTO.builder()
                        .response(pedidoDtoRespuesta)
                        .code(HttpStatus.OK.value())
                        .message("Pedido encontrado exitosamente")
                        .build();
            } else {
                return ResponseDTO.builder()
                        .message("Pedido no encontrado")
                        .code(HttpStatus.NOT_FOUND.value())
                        .build();
            }
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error inesperado: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

}