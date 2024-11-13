package com.tienda.facil.core.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    /**
     * Genera un reporte PDF de clientes usando JasperReports.
     *
     * @param clients Lista de clientes para el reporte
     * @param templatePath Ruta al archivo .jrxml del template
     * @return byte[] con el contenido del PDF
     * @throws ResponseStatusException si hay algún error en la generación
     */
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
}