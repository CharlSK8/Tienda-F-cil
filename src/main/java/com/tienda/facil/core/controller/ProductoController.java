package com.tienda.facil.core.controller;

import com.tienda.facil.core.dto.request.producto.ProductoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de productos.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tienda/facil/api/v1/productos")
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Constructor del controlador ProductoController.
     *
     * @param productoService Servicio para gestionar los productos.
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Crea un nuevo producto en la base de datos.
     *
     * @param productoDto   DTO con la información del producto.
     * @param bindingResult Resultado de la validación de la solicitud.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Crear Producto", description = "Crea un nuevo producto en la base de datos")
    @PostMapping
    public ResponseEntity<ResponseDTO<Object>> crearProducto(@Valid @RequestBody ProductoDto productoDto, BindingResult bindingResult) {
        ResponseDTO<Object> response = productoService.crearProducto(productoDto, bindingResult);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Obtiene una lista de todos los productos activos.
     *
     * @return ResponseEntity con la lista de productos activos.
     */
    @Operation(summary = "Obtener todos los Productos", description = "Obtiene una lista de todos los productos activos")
    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> obtenerTodosLosProductos() {
        ResponseDTO<Object> response = productoService.obtenerTodosLosProductos();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id            ID del producto a actualizar.
     * @param productoDto   DTO con la nueva información del producto.
     * @param bindingResult Resultado de la validación de la solicitud.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Actualizar Producto", description = "Actualiza los datos de un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Object>> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto productoDto, BindingResult bindingResult) {
        ResponseDTO<Object> response = productoService.actualizarProducto(id, productoDto, bindingResult);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Marca un producto como inactivo en lugar de eliminarlo físicamente.
     *
     * @param id ID del producto a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Eliminar Producto", description = "Marca un producto como inactivo en lugar de eliminarlo físicamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Object>> eliminarProducto(@PathVariable Long id) {
        ResponseDTO<Object> response = productoService.eliminarProductoLogico(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * Activa un producto que fue eliminado lógicamente.
     *
     * @param id ID del producto a activar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @Operation(summary = "Activar Producto", description = "Activa un producto que fue eliminado lógicamente")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto activado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Producto ya está activo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PutMapping("/{id}/activar")
    public ResponseEntity<ResponseDTO<Object>> activarProducto(@PathVariable Long id) {
        ResponseDTO<Object> response = productoService.activarProducto(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

}