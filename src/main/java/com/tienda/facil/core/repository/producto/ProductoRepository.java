package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.Pedido;
import com.tienda.facil.core.model.producto.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {


}