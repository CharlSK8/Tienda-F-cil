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

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Crear Producto", description = "Crea un nuevo producto en la base de datos")
    @PostMapping
    public ResponseEntity<ResponseDTO> crearProducto(
            @Valid @RequestBody ProductoDto productoDto,
            BindingResult bindingResult) {
        ResponseDTO<Object> response = productoService.crearProducto(productoDto, bindingResult);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener Producto por ID", description = "Obtiene un producto por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtenerProducto(@PathVariable Long id) {
        ResponseDTO<Object> response = productoService.obtenerProductoPorId(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener todos los Productos", description = "Obtiene una lista de todos los productos activos")
    @GetMapping
    public ResponseEntity<ResponseDTO> obtenerTodosLosProductos() {
        ResponseDTO<Object> response = productoService.obtenerTodosLosProductos();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Actualizar Producto", description = "Actualiza los datos de un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDto productoDto,
            BindingResult bindingResult) {
        ResponseDTO<Object> response = productoService.actualizarProducto(id, productoDto, bindingResult);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Eliminar Producto", description = "Marca un producto como inactivo en lugar de eliminarlo físicamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarProducto(@PathVariable Long id) {
        ResponseDTO<Object> response = productoService.eliminarProductoLogico(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}