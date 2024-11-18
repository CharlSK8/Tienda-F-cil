package com.tienda.facil.core.service.impl;

import com.tienda.facil.core.dto.request.LoginRequestDTO;
import com.tienda.facil.core.dto.request.ClienteRegisterRequestDTO;
import com.tienda.facil.core.dto.response.TokenResponse;
import com.tienda.facil.core.mapper.IClienteMapper;
import com.tienda.facil.core.model.Token;
import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.repository.TokenRepository;
import com.tienda.facil.core.service.IAuthService;
import com.tienda.facil.core.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IClienteMapper clienteMapper;

    @Override
    public Result<TokenResponse, String> register(ClienteRegisterRequestDTO clienteRegisterRequestDTO) {
        var user = buildCliente(clienteRegisterRequestDTO);
        var userSaved = clienteRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(userSaved, jwtToken);
        return Result.<TokenResponse, String>builder().build().success(new TokenResponse(jwtToken, refreshToken));
    }

    @Override
    public Result<TokenResponse, String> login(LoginRequestDTO loginRequest) {

        final Optional<Cliente> userOptional = clienteRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isEmpty()){
            return createErrorResult("Email not found", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = userOptional.get();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    cliente.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .toList()));
        } catch (AuthenticationException e) {
            return createErrorResult("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        var jwtToken = jwtService.generateToken(cliente);
        var refreshToken = jwtService.generateRefreshToken(cliente);
        revokeAllUserTokens(cliente);
        saveUserToken(cliente, jwtToken);
        return Result.<TokenResponse, String>builder().build().success(new TokenResponse(jwtToken, refreshToken));
    }

    @Override
    public Result<TokenResponse, String> refreshToken(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return createErrorResult("Invalid authorization header format", HttpStatus.BAD_REQUEST);
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            return createErrorResult("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }

        final Optional<Cliente> userOptional = clienteRepository.findByEmail(userEmail);

        if(userOptional.isEmpty()){
            return createErrorResult("User not found", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = userOptional.get();

        if(!jwtService.isTokenValid(refreshToken, cliente)){
            return createErrorResult("Invalid or expired refresh token", HttpStatus.UNAUTHORIZED);
        }

        final Optional<Token> optionalToken = tokenRepository.findByToken(refreshToken);
        if(optionalToken.isEmpty()){
            return createErrorResult("Token not found", HttpStatus.BAD_REQUEST);
        }

        Token token = optionalToken.get();
        if (token.isExpired() || token.isRevoked()) {
            return createErrorResult("The token has expired or has been revoked.", HttpStatus.UNAUTHORIZED);
        }

        final String accessToken = jwtService.generateToken(cliente);
        revokeAllUserTokens(cliente);
        saveUserToken(cliente, accessToken);
        return Result.<TokenResponse, String>builder().build().success(new TokenResponse(accessToken, refreshToken));

    }

    @Override
    public Result<String, String> logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.<String, String>builder().build()
                    .fail(Collections.singletonList("Invalid authorization header format"), HttpStatus.BAD_REQUEST.value());
        }
        final String jwtToken = authHeader.substring(7);
        final Optional<Token> optionalToken = tokenRepository.findByToken(jwtToken);
        if(optionalToken.isEmpty()){
            return Result.<String, String>builder().build()
                    .fail(Collections.singletonList("Token not found"), HttpStatus.BAD_REQUEST.value());
        }
        Token token = optionalToken.get();
        token.setRevoked(true);
        token.setExpired(true);
        tokenRepository.save(token);
        SecurityContextHolder.clearContext();
        return Result.<String, String>builder().build().success("Logout successful");
    }

    private void saveUserToken(Cliente cliente, String  jwtToken) {
        var token = Token.builder()
                .cliente(cliente)
                .token(jwtToken)
                .type(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Cliente cliente) {
        final List<Token> validUserToken = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByClienteId(cliente.getId());
        if (!validUserToken.isEmpty()) {
            for (final Token token : validUserToken) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserToken);
        }

    }

    private Result<TokenResponse, String> createErrorResult(String errorMessage, HttpStatus httpStatus) {
        return Result.<TokenResponse, String>builder().build()
                .fail(Collections.singletonList(errorMessage), httpStatus.value());
    }

    private Cliente buildCliente(ClienteRegisterRequestDTO clienteRegisterRequestDTO){
        clienteRegisterRequestDTO.setPassword(passwordEncoder.encode(clienteRegisterRequestDTO.getPassword()));
        return clienteMapper.toCliente(clienteRegisterRequestDTO);
    }
}
