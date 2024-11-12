package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad ClienteModel.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    /**
     * Busca un cliente por su correo electrónico.
     *
     * @param email el correo electrónico del cliente
     * @return un Optional que contiene el cliente si se encuentra, o vacío si no
     */
    Optional<ClienteModel> findByEmail(String email);  // Método personalizado

}