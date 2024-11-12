package com.tienda.facil.core.controller.producto;

import com.tienda.facil.core.dto.producto.CategoriaProductoDto;
import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.service.producto.CategoriaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de categorías de productos.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tienda/facil/api/v1/categorias")
@Tag(name = "CategoriaProductoController", description = "Gestión de categorías de productos")
public class CategoriaProductoController {
    private final CategoriaProductoService categoriaProductoService;

    /**
     * Constructor del controlador que inyecta el servicio de categoría de producto.
     *
     * @param categoriaProductoService Servicio para la gestión de categorías de productos.
     */
    public CategoriaProductoController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    /**
     * Crea una nueva categoría de producto.
     *
     * @param categoriaProductoDto Datos de la categoría a crear.
     * @return La respuesta en un {@link ResponseEntity} con un {@link ResponseDTO}.
     */
    @Operation(summary = "Crea categoría", description = "Guarda una nueva categoría de producto")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> crearCategoria(@Valid @RequestBody CategoriaProductoDto categoriaProductoDto) {
        ResponseDTO response = categoriaProductoService.crearCategoria(categoriaProductoDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}