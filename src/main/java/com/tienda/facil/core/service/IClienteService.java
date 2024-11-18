package com.tienda.facil.core.service;

import com.tienda.facil.core.dto.request.ActualizarClienteRequestDTO;
import com.tienda.facil.core.util.result.Result;

import java.util.List;

public interface IClienteService {
    Result<String, String> actualizarCliente(ActualizarClienteRequestDTO actualizarClienteRequestDTO, Long idCliente);
    byte[] exportClientReport(List<?> clients, String templatePath);
}
