package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.producto.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {
}