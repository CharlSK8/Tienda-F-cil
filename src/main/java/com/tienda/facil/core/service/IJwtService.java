package com.tienda.facil.core.service;

import com.tienda.facil.core.model.Cliente;

public interface IJwtService {
    String generateToken(final Cliente cliente);
    String generateRefreshToken(final Cliente cliente);
    String extractUsername(String token);
    boolean isTokenValid(String token, Cliente cliente);
}
