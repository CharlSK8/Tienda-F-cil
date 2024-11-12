package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.dto.producto.CategoriaProductoDto;
import com.tienda.facil.core.model.producto.CategoriaProductoModel;
import com.tienda.facil.core.repository.producto.CategoriaProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    public CategoriaProductoService(CategoriaProductoRepository categoriaProductoRepository) {
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoriaProductoDto Datos de la categoría a crear.
     * @return Un {@link ResponseDTO} con la categoría creada o un mensaje de error.
     */
    public ResponseDTO crearCategoria(CategoriaProductoDto categoriaProductoDto) {
        try {
            // Convierte el DTO al modelo de entidad
            CategoriaProductoModel categoriaModel = getCategoriaModelFromDto(categoriaProductoDto);

            // Guarda la nueva categoría en la base de datos
            CategoriaProductoModel nuevaCategoria = categoriaProductoRepository.save(categoriaModel);

            return ResponseDTO.builder()
                    .response(nuevaCategoria)
                    .code(HttpStatus.CREATED.value())
                    .message("Categoría creada exitosamente")
                    .build();

        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error al crear la categoría: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    private CategoriaProductoModel getCategoriaModelFromDto(CategoriaProductoDto categoriaProductoDto) {
        CategoriaProductoModel categoriaProductoModel = new CategoriaProductoModel();
        categoriaProductoModel.setCategoriaProducto(categoriaProductoDto.getCategoriaProducto());
        categoriaProductoModel.setDescripcion(categoriaProductoDto.getDescripcion());
        categoriaProductoModel.setFechaCreacion(new Date()); // Fecha de creación establecida automáticamente
        categoriaProductoModel.setFechaModificacion(new Date()); // Fecha de modificación inicial

        categoriaProductoModel.setEstadoCategoria(categoriaProductoDto.getEstadoCategoria());
        return categoriaProductoModel;
    }

}