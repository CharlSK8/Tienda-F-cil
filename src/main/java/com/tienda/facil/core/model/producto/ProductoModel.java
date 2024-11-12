package com.tienda.facil.core.model.producto;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "producto")
public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private BigDecimal precioUnitario;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaProductoModel categoriaProducto;

}