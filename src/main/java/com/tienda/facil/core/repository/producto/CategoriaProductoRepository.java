package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.CategoriaProducto;
import com.tienda.facil.core.util.enums.EstadoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {

    // Método para buscar categorías por estado
    List<CategoriaProducto> findByEstadoCategoria(EstadoCategoria estadoCategoria);

}