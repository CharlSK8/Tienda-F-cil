package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.dto.request.producto.ProductoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.model.CategoriaProducto;
import com.tienda.facil.core.model.Producto;
import com.tienda.facil.core.repository.producto.CategoriaProductoRepository;
import com.tienda.facil.core.repository.producto.ProductoRepository;
import com.tienda.facil.core.util.constants.Constants;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar los productos.
 */
@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;

    /**
     * Crea un nuevo producto en la base de datos.
     *
     * @param productoDto DTO con la información del producto.
     * @param bindingResult Resultado de la validación de la solicitud.
     * @return ResponseDTO con el resultado de la operación.
     */
    @Transactional
    public ResponseDTO<Object> crearProducto(@Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        try {
            CategoriaProducto categoria = categoriaProductoRepository.findById(productoDto.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            Producto producto = convertToEntity(productoDto, categoria);
            producto.setActivo(true);

            Producto nuevoProducto = productoRepository.save(producto);

            return ResponseDTO.builder()
                    .response(nuevoProducto)
                    .code(HttpStatus.CREATED.value())
                    .message("Producto creado exitosamente")
                    .build();

        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error inesperado: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id ID del producto a actualizar.
     * @param productoDto DTO con la nueva información del producto.
     * @param bindingResult Resultado de la validación de la solicitud.
     * @return ResponseDTO con el resultado de la operación.
     */
    @Transactional
    public ResponseDTO<Object> actualizarProducto(Long id, @Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        try {
            Producto producto = productoRepository.findByIdAndActivoTrue(id)
                    .orElseThrow(() -> new IllegalArgumentException(Constants.PRODUCTO_NO_ENCONTRADO));

            CategoriaProducto categoria = categoriaProductoRepository.findById(productoDto.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            producto.setNombre(productoDto.getNombre());
            producto.setDescripcion(productoDto.getDescripcion());
            producto.setPrecioUnitario(productoDto.getPrecioUnitario());
            producto.setStock(productoDto.getStock());
            producto.setCategoriaProducto(categoria);

            Producto productoActualizado = productoRepository.save(producto);

            return ResponseDTO.builder()
                    .response(productoActualizado)
                    .code(HttpStatus.OK.value())
                    .message("Producto actualizado exitosamente")
                    .build();

        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error inesperado: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    /**
     * Obtiene una lista de todos los productos activos.
     *
     * @return ResponseDTO con la lista de productos activos.
     */
    public ResponseDTO<Object> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findByActivoTrue();

        return ResponseDTO.builder()
                .response(productos)
                .code(HttpStatus.OK.value())
                .message("Productos obtenidos exitosamente")
                .build();
    }

    /**
     * Marca un producto como inactivo en lugar de eliminarlo físicamente.
     *
     * @param id ID del producto a eliminar.
     * @return ResponseDTO con el resultado de la operación.
     */
    @Transactional
    public ResponseDTO<Object> eliminarProductoLogico(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.setActivo(false);
        productoRepository.save(producto);

        return ResponseDTO.builder()
                .message("Producto eliminado lógicamente")
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * Convierte un ProductoDto a una entidad Producto.
     *
     * @param productoDto DTO con la información del producto.
     * @param categoria Categoría del producto.
     * @return Producto convertido.
     */
    private Producto convertToEntity(ProductoDto productoDto, CategoriaProducto categoria) {
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setStock(productoDto.getStock());
        producto.setCategoriaProducto(categoria);
        return producto;
    }

    /**
     * Activa un producto que fue eliminado lógicamente.
     *
     * @param id ID del producto a activar.
     * @return ResponseDTO con el resultado de la operación.
     */
    @Transactional
    public ResponseDTO<Object> activarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.isActivo()) {
            return ResponseDTO.builder()
                    .message("El producto ya está activo")
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        producto.setActivo(true);
        productoRepository.save(producto);

        return ResponseDTO.builder()
                .message("Producto activado exitosamente")
                .code(HttpStatus.OK.value())
                .build();
    }
}