package com.tienda.facil.core.mapper;

import com.tienda.facil.core.dto.request.ClienteRegisterRequestDTO;
import com.tienda.facil.core.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IClienteMapper {

    Cliente toCliente(ClienteRegisterRequestDTO clienteRegisterRequestDTO);
}
