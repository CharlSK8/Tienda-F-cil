package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.ClienteModel;
import com.tienda.facil.core.model.PedidoModel;
import com.tienda.facil.core.model.PrioridadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad PedidoModel.
 * Extiende JpaRepository para proporcionar métodos CRUD básicos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    /*
     * create o insert en DDBB
     * No es necesario agregar métodos personalizados para: Create, Read, Update y Delete (CRUD), ya que JpaRepository ya los tiene implementados.
     */

    /**
     * Encuentra un pedido por cliente y prioridad.
     *
     * @param cliente   el cliente asociado al pedido
     * @param prioridad la prioridad del pedido
     * @return un Optional que contiene el PedidoModel si se encuentra, o vacío si no
     */
    Optional<PedidoModel> findFirstByClienteAndPrioridad(ClienteModel cliente, PrioridadModel prioridad);
}