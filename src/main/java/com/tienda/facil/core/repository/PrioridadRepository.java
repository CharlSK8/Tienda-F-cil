package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.Prioridad;
import com.tienda.facil.core.util.enums.PrioridadPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la entidad PrioridadModel.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
public interface PrioridadRepository extends JpaRepository<Prioridad, Long> {

    /**
     * Busca una prioridad por su nombre.
     *
     * @param nombre el nombre de la prioridad
     * @return un Optional que contiene la prioridad si se encuentra, o vacío si no
     */
    Optional<Prioridad> findByNombre(PrioridadPedido nombre);  // Método personalizado

}