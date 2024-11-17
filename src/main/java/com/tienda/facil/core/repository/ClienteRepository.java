package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.util.enums.EstadoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByActivo(EstadoActivo activo);
}
