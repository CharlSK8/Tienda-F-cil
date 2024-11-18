package com.tienda.facil.core.service.impl;

import com.tienda.facil.core.dto.request.ActualizarClienteRequestDTO;
import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.service.IClienteService;
import com.tienda.facil.core.util.enums.EstadoActivo;
import com.tienda.facil.core.util.result.Result;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Actualiza los datos de un cliente existente en el sistema.
     *
     * Este método recibe un DTO con los nuevos datos del cliente y el ID del cliente a actualizar.
     * Los campos proporcionados en el DTO serán actualizados, y los que no estén presentes
     * no serán modificados. Si el cliente no existe, se retorna un error.
     *
     * @param actualizarClienteRequestDTO El DTO que contiene los datos a actualizar del cliente.
     * @param idCliente El ID único del cliente que se desea actualizar.
     * @return Un objeto Result que contiene el resultado de la operación: un mensaje de éxito
     *         o un mensaje de error en caso de que el cliente no se encuentre.
     */
    @Override
    public Result<String, String> actualizarCliente(ActualizarClienteRequestDTO actualizarClienteRequestDTO, Long idCliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        if(clienteOptional.isEmpty()){
            return Result.<String, String>builder().build()
                    .fail(Collections.singletonList("No se ha encontrado cliente con el id: " + idCliente),
                            HttpStatus.BAD_REQUEST.value());
        }
        Cliente cliente = clienteOptional.get();

        actualizarCampoSiValido(actualizarClienteRequestDTO.getNombre(), cliente::setNombre);
        actualizarCampoSiValido(actualizarClienteRequestDTO.getSegundoNombre(), cliente::setSegundoNombre);
        actualizarCampoSiValido(actualizarClienteRequestDTO.getApellido(), cliente::setApellido);
        actualizarCampoSiValido(actualizarClienteRequestDTO.getSegundoApellido(), cliente::setSegundoApellido);
        actualizarCampoSiValido(actualizarClienteRequestDTO.getEmail(), cliente::setEmail);
        actualizarCampoSiValido(actualizarClienteRequestDTO.getContacto(), cliente::setContacto);

        if (actualizarClienteRequestDTO.getPassword() != null && !actualizarClienteRequestDTO.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(actualizarClienteRequestDTO.getPassword()));
        }

        if (actualizarClienteRequestDTO.getRoles() != null && !actualizarClienteRequestDTO.getRoles().isEmpty()) {
            cliente.setRoles(actualizarClienteRequestDTO.getRoles());
        }

        actualizarCampoSiValido(actualizarClienteRequestDTO.getActivo(), cliente::setActivo);

        clienteRepository.save(cliente);

        return Result.<String, String>builder().build().success("Cliente actualizado correctamente");
    }

    /**
     * Genera un reporte PDF de clientes usando JasperReports.
     *
     * @param clients Lista de clientes para el reporte
     * @param templatePath Ruta al archivo .jrxml del template
     * @return byte[] con el contenido del PDF
     * @throws ResponseStatusException si hay algún error en la generación
     */
    @Override
    public byte[] exportClientReport(List<?> clients, String templatePath) {
        if (clients.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No hay datos para generar el reporte"
            );
        }

        ClassPathResource resource = new ClassPathResource(templatePath);
        if (!resource.exists()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Template de reporte no encontrado"
            );
        }

        try (InputStream inputStream = resource.getInputStream()) {
            // Preparar parámetros
            Map<String, Object> parameters = prepareParameters();

            // Crear data source
            JRDataSource dataSource = new JRBeanCollectionDataSource(clients);

            // Compilar template
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Llenar reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al leer el template del reporte"
            );
        } catch (JRException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte: " + e.getMessage()
            );
        }
    }

    /**
     * Prepara los parámetros base para el reporte.
     */
    private Map<String, Object> prepareParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Reporte de Clientes Activos");
        parameters.put("GeneratedBy", "Sistema TiendaFacil");
        parameters.put("GenerationDate", new java.util.Date());
        return parameters;
    }

    /**
     * Actualiza el valor del campo del cliente si el valor proporcionado no es nulo ni vacío.
     *
     * @param nuevoValor El nuevo valor que se desea establecer en el campo del cliente.
     * @param setter La función que establece el valor en el campo correspondiente del cliente.
     */
    private void actualizarCampoSiValido(String nuevoValor, Consumer<String> setter) {
        if (nuevoValor != null && !nuevoValor.isEmpty()) {
            setter.accept(nuevoValor);
        }
    }

    /**
     * Actualiza el valor del campo del cliente si el valor proporcionado no es nulo.
     *
     * @param nuevoValor El nuevo valor de tipo {@link EstadoActivo} que se desea establecer en el campo del cliente.
     * @param setter La función que establece el valor en el campo correspondiente del cliente.
     */
    private void actualizarCampoSiValido(EstadoActivo nuevoValor, Consumer<EstadoActivo> setter) {
        if (nuevoValor != null) {
            setter.accept(nuevoValor);
        }
    }
}
