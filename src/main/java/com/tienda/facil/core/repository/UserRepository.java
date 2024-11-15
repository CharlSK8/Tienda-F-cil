package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
}
