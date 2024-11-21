package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.DetallePedidoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.model.DetallePedido;
import com.tienda.facil.core.model.Pedido;
import com.tienda.facil.core.model.Producto;
import com.tienda.facil.core.repository.DetallePedidoRepository;
import com.tienda.facil.core.repository.PedidoRepository;
import com.tienda.facil.core.repository.producto.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar los detalles de los pedidos.
 */
@Service
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    /**
     * Constructor del servicio DetallePedidoService.
     *
     * @param detallePedidoRepository Repositorio de detalles de pedido.
     * @param pedidoRepository        Repositorio de pedidos.
     * @param productoRepository      Repositorio de productos.
     */
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository, PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Crea un nuevo detalle de pedido.
     *
     * @param detalleDto DTO con la información del detalle de pedido.
     * @return ResponseDTO con el resultado de la operación.
     */
    public ResponseDTO<Object> crearDetallePedido(DetallePedidoDto detalleDto) {
        try {
            Pedido pedido = pedidoRepository.findById(detalleDto.getPedidoId())
                    .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

            Producto producto = productoRepository.findByIdAndActivoTrue(detalleDto.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(producto);
            detallePedido.setCantidad(detalleDto.getCantidad());
            detallePedido.setSubtotal(producto.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleDto.getCantidad())));
            detallePedido.setActivo(true);

            DetallePedido nuevoDetalle = detallePedidoRepository.save(detallePedido);

            return ResponseDTO.builder()
                    .response(nuevoDetalle)
                    .code(HttpStatus.CREATED.value())
                    .message("Detalle de pedido creado exitosamente")
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error al crear el detalle de pedido: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    /**
     * Obtiene los detalles de pedido por el ID del pedido.
     *
     * @param pedidoId ID del pedido.
     * @return ResponseDTO con la lista de detalles de pedido.
     */
    public ResponseDTO<Object> obtenerDetallesPorPedido(Long pedidoId) {
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoIdAndActivoTrue(pedidoId);

        return ResponseDTO.builder()
                .response(detalles)
                .code(HttpStatus.OK.value())
                .message("Detalles de pedido obtenidos exitosamente")
                .build();
    }

    /**
     * Elimina lógicamente un detalle de pedido por su ID.
     *
     * @param id ID del detalle de pedido.
     * @return ResponseDTO con el resultado de la operación.
     */
    public ResponseDTO<Object> eliminarDetallePedido(Long id) {
        Optional<DetallePedido> detalleOpt = detallePedidoRepository.findByIdAndActivoTrue(id);

        if (detalleOpt.isPresent()) {
            DetallePedido detalle = detalleOpt.get();
            detalle.setActivo(false);
            detallePedidoRepository.save(detalle);

            return ResponseDTO.builder()
                    .message("Detalle de pedido eliminado lógicamente")
                    .code(HttpStatus.OK.value())
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message("Detalle de pedido no encontrado o ya eliminado")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }
}