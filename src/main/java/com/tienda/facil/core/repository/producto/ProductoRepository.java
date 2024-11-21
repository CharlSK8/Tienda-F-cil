package com.tienda.facil.core.repository.producto;

import com.tienda.facil.core.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    /**
     * Encuentra todos los productos activos.
     *
     * @return Lista de productos activos.
     */
    List<Producto> findByActivoTrue();

    /**
     * Encuentra un producto activo por su ID.
     *
     * @param id El ID del producto.
     * @return Un Optional que contiene el producto si está activo.
     */
    Optional<Producto> findByIdAndActivoTrue(Long id);
}