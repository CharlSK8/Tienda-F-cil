package com.tienda.facil.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tienda.facil.core.util.enums.EstadoCategoria;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Modelo de datos para la entidad CategoriaProducto.
 */
@Data
@Entity
@Table(name = "categoria_producto")
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private com.tienda.facil.core.util.enums.CategoriaProducto categoriaProducto;

    @Column(name = "descripcion_producto")
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_categoria")
    private EstadoCategoria estadoCategoria;

    @OneToMany(mappedBy = "categoriaProducto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Producto> productos;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
        this.estadoCategoria = EstadoCategoria.ACTIVA;
    }
}