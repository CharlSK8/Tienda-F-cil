package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad DetallePedido.
 */
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    /**
     * Encuentra todos los detalles de pedido activos por el ID del pedido.
     *
     * @param pedidoId El ID del pedido.
     * @return Una lista de detalles de pedido activos.
     */
    List<DetallePedido> findByPedidoIdAndActivoTrue(Long pedidoId);

    /**
     * Encuentra un detalle de pedido activo por su ID.
     *
     * @param id El ID del detalle del pedido.
     * @return Un Optional que contiene el detalle del pedido si se encuentra.
     */
    Optional<DetallePedido> findByIdAndActivoTrue(Long id);
}