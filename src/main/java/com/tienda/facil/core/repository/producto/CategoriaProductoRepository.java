package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.producto.CategoriaProductoModel;
import com.tienda.facil.core.utils.enums.EstadoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProductoModel, Long> {

    // Método para buscar categorías por estado
    List<CategoriaProductoModel> findByEstadoCategoria(EstadoCategoria estadoCategoria);

}