package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.DetallePedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoModel, Long> {
}