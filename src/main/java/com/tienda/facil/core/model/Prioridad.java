package com.tienda.facil.core.model;

import com.tienda.facil.core.util.enums.PrioridadPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Modelo de datos para la entidad Prioridad.
 */
@Data
@Entity
@Table(name = "prioridad")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PrioridadPedido nombre;

    @OneToMany(mappedBy = "prioridad", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
}