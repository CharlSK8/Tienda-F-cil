package com.tienda.facil.core.model;

import com.tienda.facil.core.util.enums.EstadoCategoria;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Modelo de datos para la entidad CategoriaProducto.
 */
@Data
@Entity
@Table(name = "categoria_producto")
public class CategoriaProducto {

    /**
     * Identificador único de la categoría de producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Categoría del producto.
     */
    @Enumerated(EnumType.STRING)
    private com.tienda.facil.core.util.enums.CategoriaProducto categoriaProducto;

    /**
     * Descripción de la categoría del producto.
     */
    @Column(name = "descripcion_producto")
    private String descripcion;

    /**
     * Fecha de creación de la categoría del producto.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    /**
     * Fecha de la última modificación de la categoría del producto.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    /**
     * Estado actual de la categoría del producto.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_categoria")
    private EstadoCategoria estadoCategoria;
}