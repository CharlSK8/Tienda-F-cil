package com.tienda.facil.core.model;

import com.tienda.facil.core.utils.enums.EstadoActivo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Modelo de datos para la entidad Cliente.
 * Representa un cliente de la tienda.
 */
@Data
@Entity
@Table(name = "cliente")  // Nombre de la tabla
public class ClienteModel {

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Segundo nombre del cliente.
     */
    private String segundoNombre;

    /**
     * Apellido del cliente.
     */
    private String apellido;

    /**
     * Segundo apellido del cliente.
     */
    private String segundoApellido;

    /**
     * Teléfono del cliente.
     */
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    private String email;

    /**
     * Fecha de registro del cliente.
     */
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    /**
     * Estado activo del cliente.
     */
    @Enumerated(EnumType.STRING)
    private EstadoActivo activo;

    /**
     * Relación de uno a muchos con Pedido.
     * Representa los pedidos realizados por el cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PedidoModel> pedidos;
}