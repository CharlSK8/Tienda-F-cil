package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.dto.CategoriaDto;
import com.tienda.facil.core.model.producto.CategoriaProductoModel;
import com.tienda.facil.core.repository.producto.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaProductoService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaProductoService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Obtiene una lista de todas las categorías de productos.
     *
     * @return una lista de objetos {@link CategoriaDto}.
     */
    public List<CategoriaDto> obtenerCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una categoría específica por su ID.
     *
     * @param id el ID de la categoría a obtener.
     * @return un objeto {@link Optional} que contiene el {@link CategoriaDto} si se encuentra, o vacío si no.
     */
    public Optional<CategoriaDto> obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * Guarda una nueva categoría en la base de datos.
     *
     * @param categoriaDto el objeto {@link CategoriaDto} que contiene los datos de la categoría.
     * @return un objeto {@link CategoriaDto} que representa la categoría guardada.
     */
    public CategoriaDto guardarCategoria(CategoriaDto categoriaDto) {
        CategoriaProductoModel categoriaModel = convertToEntity(categoriaDto);
        CategoriaProductoModel nuevaCategoria = categoriaRepository.save(categoriaModel);
        return convertToDto(nuevaCategoria);
    }

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param id el ID de la categoría a actualizar.
     * @param categoriaDto el objeto {@link CategoriaDto} con los datos actualizados.
     * @return un objeto {@link CategoriaDto} que representa la categoría actualizada.
     */
    public CategoriaDto actualizarCategoria(Long id, CategoriaDto categoriaDto) {
        CategoriaProductoModel categoriaModel = convertToEntity(categoriaDto);
        categoriaModel.setId(id);  // Asegurarse de asignar el ID
        CategoriaProductoModel categoriaActualizada = categoriaRepository.save(categoriaModel);
        return convertToDto(categoriaActualizada);
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param id el ID de la categoría a eliminar.
     */
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    /**
     * Convierte un {@link CategoriaProductoModel} a un {@link CategoriaDto}.
     *
     * @param categoria el modelo de entidad de la categoría.
     * @return un objeto {@link CategoriaDto}.
     */
    private CategoriaDto convertToDto(CategoriaProductoModel categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

    /**
     * Convierte un {@link CategoriaDto} a un {@link CategoriaProductoModel}.
     *
     * @param dto el objeto DTO de la categoría.
     * @return un objeto {@link CategoriaProductoModel}.
     */
    private CategoriaProductoModel convertToEntity(CategoriaDto dto) {
        CategoriaProductoModel categoria = new CategoriaProductoModel();
        categoria.setNombre(dto.getNombre());
        return categoria;
    }
}