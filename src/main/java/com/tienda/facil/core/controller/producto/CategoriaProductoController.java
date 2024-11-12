package com.tienda.facil.core.controller.producto;

import com.tienda.facil.core.dto.CategoriaDto;
import com.tienda.facil.core.service.CategoriaService;
import com.tienda.facil.core.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de categorías de productos.
 */
@RestController
@RequestMapping("/api/categorias")
@Tag(name = "CategoriaProductoController", description = "Gestión de categorías de productos")
public class CategoriaProductoController {

    private final CategoriaService categoriaService;

    public CategoriaProductoController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Obtiene todas las categorías de productos.
     * @return Lista de categorías en un {@link ResponseDTO}.
     */
    @Operation(summary = "Obtener todas las categorías", description = "Obtiene todas las categorías de productos")
    @GetMapping
    public ResponseEntity<ResponseDTO> obtenerCategorias() {
        List<CategoriaDto> categorias = categoriaService.obtenerCategorias();
        return ResponseEntity.ok(ResponseDTO.builder()
                .response(categorias)
                .code(HttpStatus.OK.value())
                .message("Categorías obtenidas exitosamente")
                .build());
    }

    /**
     * Obtiene una categoría específica por su ID.
     * @param id ID de la categoría a obtener.
     * @return La categoría encontrada o un mensaje de error en un {@link ResponseDTO}.
     */
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtenerCategoria(@PathVariable Long id) {
        return categoriaService.obtenerCategoria(id)
                .map(categoria -> ResponseEntity.ok(ResponseDTO.builder()
                        .response(categoria)
                        .code(HttpStatus.OK.value())
                        .message("Categoría obtenida exitosamente")
                        .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                        .message("Categoría no encontrada")
                        .code(HttpStatus.NOT_FOUND.value())
                        .build()));
    }

    /**
     * Guarda una nueva categoría de producto.
     * @param categoriaDto Datos de la categoría a guardar.
     * @return La categoría guardada en un {@link ResponseDTO}.
     */
    @Operation(summary = "Guardar categoría", description = "Guarda una nueva categoría de producto")
    @PostMapping
    public ResponseEntity<ResponseDTO> guardarCategoria(@RequestBody CategoriaDto categoriaDto) {
        CategoriaDto nuevaCategoria = categoriaService.guardarCategoria(categoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.builder()
                .response(nuevaCategoria)
                .code(HttpStatus.CREATED.value())
                .message("Categoría guardada exitosamente")
                .build());
    }

    /**
     * Actualiza una categoría existente.
     * @param id ID de la categoría a actualizar.
     * @param categoriaDto Datos actualizados de la categoría.
     * @return La categoría actualizada en un {@link ResponseDTO}.
     */
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDto categoriaDto) {
        CategoriaDto categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDto);
        return ResponseEntity.ok(ResponseDTO.builder()
                .response(categoriaActualizada)
                .code(HttpStatus.OK.value())
                .message("Categoría actualizada exitosamente")
                .build());
    }

    /**
     * Elimina una categoría por su ID.
     * @param id ID de la categoría a eliminar.
     * @return Mensaje de confirmación en un {@link ResponseDTO}.
     */
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message("Categoría eliminada exitosamente")
                .code(HttpStatus.OK.value())
                .build());
    }
}