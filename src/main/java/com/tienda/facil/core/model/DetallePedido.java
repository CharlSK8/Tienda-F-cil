package com.tienda.facil.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Entidad que representa el detalle de un pedido.
 */
@Data
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    /**
     * Identificador único del detalle del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido al que pertenece este detalle.
     */
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    /**
     * Producto incluido en este detalle del pedido.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /**
     * Cantidad del producto en este detalle del pedido.
     */
    @Column(nullable = false)
    private int cantidad;

    /**
     * Subtotal del producto en este detalle del pedido.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    /**
     * Indica si el detalle del pedido está activo (para eliminación lógica).
     */
    @Column(nullable = false)
    private boolean activo = true;
}