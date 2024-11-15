package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.request.ClienteRegisterRequestDTO;
import com.tienda.facil.core.dto.request.LoginRequestDTO;
import com.tienda.facil.core.dto.response.TokenResponse;
import com.tienda.facil.core.util.result.Result;

public interface IAuthService {
    Result<TokenResponse, String> register(ClienteRegisterRequestDTO clienteRegisterRequestDTOst);
    Result<TokenResponse, String> login(LoginRequestDTO loginRequest);
    Result<TokenResponse, String> refreshToken(String authHeader);
    Result<String, String> logout(String authHeader);
}
