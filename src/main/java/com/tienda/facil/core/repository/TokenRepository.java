package com.tienda.facil.core.repository;

import com.tienda.facil.core.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllValidIsFalseOrRevokedIsFalseByClienteId(Long userId);
    Optional<Token> findByToken(String jwtToken);
}
