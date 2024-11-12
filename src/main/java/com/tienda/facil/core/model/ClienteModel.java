package com.tienda.facil.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Modelo de datos para Cliente.
 * Representa un cliente en la base de datos.
 */
@Data
@Entity
@Table(name = "cliente")
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
     * Correo electrónico del cliente.
     */
    private String email;

    /**
     * Lista de pedidos asociados al cliente.
     * La anotación @JsonBackReference rompe la recursividad al serializar cliente -> pedidos.
     */
    @OneToMany(mappedBy = "cliente")
    @JsonBackReference
    private List<PedidoModel> pedidos;
}