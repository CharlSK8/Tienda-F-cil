package com.tienda.facil.core.model;

import com.tienda.facil.core.util.enums.PrioridadPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Modelo de datos para la entidad Prioridad.
 * Representa la prioridad de un pedido.
 */
@Data
@Entity
@Table(name = "prioridad")  // Nombre de la tabla
public class PrioridadModel {

    /**
     * Identificador único de la prioridad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la prioridad.
     */
    @Enumerated(EnumType.STRING)
    private PrioridadPedido nombre;

    /**
     * Relación uno a muchos con Pedido.
     * Representa los pedidos asociados a esta prioridad.
     */
    @OneToMany(mappedBy = "prioridad", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
}