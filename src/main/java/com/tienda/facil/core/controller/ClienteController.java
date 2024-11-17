package com.tienda.facil.core.controller;

import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.service.ReportService;
import com.tienda.facil.core.util.enums.EstadoActivo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes")
@RequestMapping("/tienda/facil/api/v1/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ReportService reportService;
    private final ClienteRepository clienteRepository;

    public ClienteController(ReportService reportService, ClienteRepository clienteRepository) {
        this.reportService = reportService;
        this.clienteRepository = clienteRepository;
    }
    @Operation(summary = "Reporte clientes activos", description = "Genera un reporte de todos los clientes los cuales se encuentran en estado ACTIVO.")
    @GetMapping("/reporte-activos")
    public ResponseEntity<byte[]> generateActiveClientsReport() {
        logger.info("Iniciando generación de reporte de clientes activos");

        // Obtener clientes activos
        List<Cliente> activeClients = clienteRepository.findByActivo(EstadoActivo.ACTIVO);

        if (activeClients.isEmpty()) {
            logger.warn("No se encontraron clientes activos para generar el reporte");
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "No hay clientes activos para generar el reporte"
            );
        }

        try {
            logger.debug("Generando reporte para {} clientes activos", activeClients.size());

            // Generar el reporte
            byte[] reportBytes = reportService.exportClientReport(
                    activeClients,
                    "reports/clientesActivos.jrxml"
            );

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "clientes_activos.pdf");

            logger.info("Reporte generado exitosamente");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        } catch (Exception e) {
            logger.error("Error al generar el reporte de clientes activos: {}", e.getMessage(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte: " + e.getMessage()
            );
        }
    }
}