package com.tienda.facil.core.model.producto;

import com.tienda.facil.core.utils.enums.CategoriaProducto;
import com.tienda.facil.core.utils.enums.EstadoCategoria;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "categoria_producto")
public class CategoriaProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoriaProducto categoriaProducto;

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
}