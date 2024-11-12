package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.producto.CategoriaProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaProductoModel, Long> {
}