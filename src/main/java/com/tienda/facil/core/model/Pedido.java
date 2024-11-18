package com.tienda.facil.core.model;

import com.tienda.facil.core.util.enums.EstadoPedido;
import com.tienda.facil.core.util.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Modelo de datos para la entidad Pedido.
 * Representa un pedido realizado por un cliente.
 */
@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "pedido")  // Nombre de la tabla
public class Pedido {

    /**
     * Identificador único del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relación muchos a uno con Cliente.
     * Representa el cliente que realiza el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    /**
     * Relación muchos a uno con Prioridad.
     * Representa la prioridad del pedido.
     */
    @ManyToOne
    @JoinColumn(name = "prioridad_id", referencedColumnName = "id")
    private Prioridad prioridad;

    /**
     * Fecha en la que se realizó el pedido.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    /**
     * Fecha estimada de entrega del pedido.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    /**
     * Estado actual del pedido.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido")
    private EstadoPedido estadoPedido;

    /**
     * Monto total del pedido.
     */
    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    /**
     * Método de pago utilizado para el pedido.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    /**
     * Dirección de envío del pedido.
     */
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    /**
     * Fecha de la última modificación del pedido.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    /**
     * Número de seguimiento del pedido.
     */
    @Column(name = "numero_seguimiento")
    private String numeroSeguimiento;

    /**
     * Estado de activación del pedido (para eliminación lógica).
     * True significa que el pedido está activo, false indica que está eliminado.
     */
    @Column(name = "activo", nullable = false)
    private boolean activo = true; // Valor predeterminado para nuevos pedidos
}