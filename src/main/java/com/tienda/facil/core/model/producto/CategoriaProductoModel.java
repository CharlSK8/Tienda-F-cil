package com.tienda.facil.core.model.producto;

import com.tienda.facil.core.utils.enums.CategoriaProducto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria_producto")
public class CategoriaProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CategoriaProducto categoriaProducto ;
}