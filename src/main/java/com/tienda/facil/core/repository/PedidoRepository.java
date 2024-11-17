package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.model.Pedido;
import com.tienda.facil.core.model.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la entidad PedidoModel.
 * Extiende JpaRepository para proporcionar métodos CRUD básicos.
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

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
    Optional<Pedido> findFirstByClienteAndPrioridad(Cliente cliente, Prioridad prioridad);
}