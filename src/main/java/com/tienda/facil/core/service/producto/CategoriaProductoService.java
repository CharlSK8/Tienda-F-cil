package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.dto.request.producto.CategoriaProductoDto;
import com.tienda.facil.core.model.CategoriaProducto;
import com.tienda.facil.core.repository.producto.CategoriaProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Servicio para la gestión de categorías de productos.
 */
@Service
public class CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    /**
     * Constructor de CategoriaProductoService.
     *
     * @param categoriaProductoRepository Repositorio de categorías de productos.
     */
    public CategoriaProductoService(CategoriaProductoRepository categoriaProductoRepository) {
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoriaProductoDto Datos de la categoría a crear.
     * @return Un {@link ResponseDTO} con la categoría creada o un mensaje de error.
     */
    public ResponseDTO<Object> crearCategoria(CategoriaProductoDto categoriaProductoDto) {
        try {
            // Convierte el DTO al modelo de entidad
            CategoriaProducto categoriaModel = getCategoriaModelFromDto(categoriaProductoDto);

            // Guarda la nueva categoría en la base de datos
            CategoriaProducto nuevaCategoria = categoriaProductoRepository.save(categoriaModel);

            return ResponseDTO.builder().response(nuevaCategoria).code(HttpStatus.CREATED.value()).message("Categoría creada exitosamente").build();

        } catch (Exception e) {
            return ResponseDTO.builder().message("Error al crear la categoría: " + e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
    }

    /**
     * Convierte un {@link CategoriaProductoDto} a un {@link CategoriaProducto}.
     *
     * @param categoriaProductoDto El objeto {@link CategoriaProductoDto} que contiene los datos de la categoría.
     * @return El objeto {@link CategoriaProducto} mapeado.
     */
    private CategoriaProducto getCategoriaModelFromDto(CategoriaProductoDto categoriaProductoDto) {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setCategoriaProducto(categoriaProductoDto.getCategoriaProducto());
        categoriaProducto.setDescripcion(categoriaProductoDto.getDescripcion());
        categoriaProducto.setFechaCreacion(new Date()); // Fecha de creación establecida automáticamente
        categoriaProducto.setFechaModificacion(new Date()); // Fecha de modificación inicial

        categoriaProducto.setEstadoCategoria(categoriaProductoDto.getEstadoCategoria());
        return categoriaProducto;
    }

    /**
     * Actualiza una categoría en la base de datos.
     *
     * @param id                   El ID de la categoría a actualizar.
     * @param categoriaProductoDto Los nuevos datos para la categoría.
     * @return Un {@link ResponseDTO} con la categoría actualizada o un mensaje de error.
     */
    public ResponseDTO<Object> actualizarCategoria(Long id, @Valid CategoriaProductoDto categoriaProductoDto) {
        try {
            // Busca la categoría a actualizar
            CategoriaProducto categoriaModel = categoriaProductoRepository.findById(id).orElseThrow(() -> new Exception("Categoría no encontrada"));

            // Actualiza los datos de la categoría
            categoriaModel.setCategoriaProducto(categoriaProductoDto.getCategoriaProducto());
            categoriaModel.setDescripcion(categoriaProductoDto.getDescripcion());
            categoriaModel.setFechaModificacion(new Date());
            categoriaModel.setEstadoCategoria(categoriaProductoDto.getEstadoCategoria());

            // Guarda los cambios en la base de datos
            CategoriaProducto categoriaActualizada = categoriaProductoRepository.save(categoriaModel);

            return ResponseDTO.builder().response(categoriaActualizada).code(HttpStatus.OK.value()).message("Categoría actualizada exitosamente").build();

        } catch (Exception e) {
            return ResponseDTO.builder().message("Error al actualizar la categoría: " + e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
    }

    /**
     * Obtiene todas las categorías de la base de datos.
     *
     * @return Un {@link ResponseDTO} con la lista de categorías obtenidas.
     */
    public ResponseDTO<Object> obtenerCategorias() {
        try {
            // Obtiene todas las categorías de la base de datos
            Iterable<CategoriaProducto> categorias = categoriaProductoRepository.findAll();

            return ResponseDTO.builder().response(categorias).code(HttpStatus.OK.value()).message("Categorías obtenidas exitosamente").build();

        } catch (Exception e) {
            return ResponseDTO.builder().message("Error al obtener las categorías: " + e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param id El ID de la categoría a eliminar.
     * @return Un {@link ResponseDTO} que indica si la eliminación fue exitosa o si ocurrió un error.
     */
    public ResponseDTO<Object> eliminarCategoria(Long id) {
        try {
            // Busca la categoría a eliminar
            CategoriaProducto categoriaModel = categoriaProductoRepository.findById(id).orElseThrow(() -> new Exception("Categoría no encontrada"));

            // Elimina la categoría de la base de datos
            categoriaProductoRepository.delete(categoriaModel);

            return ResponseDTO.builder().code(HttpStatus.OK.value()).message("Categoría eliminada exitosamente").build();

        } catch (Exception e) {
            return ResponseDTO.builder().message("Error al eliminar la categoría: " + e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
    }
}