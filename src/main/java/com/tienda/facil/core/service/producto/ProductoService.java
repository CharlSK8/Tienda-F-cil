package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.dto.request.producto.ProductoDto;
import com.tienda.facil.core.dto.response.ResponseDTO;
import com.tienda.facil.core.model.CategoriaProducto;
import com.tienda.facil.core.model.Producto;
import com.tienda.facil.core.repository.producto.CategoriaProductoRepository;
import com.tienda.facil.core.repository.producto.ProductoRepository;
import com.tienda.facil.core.util.constants.Constants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;

    public ResponseDTO<Object> crearProducto(@Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
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

    public ResponseDTO<Object> actualizarProducto(Long id, @Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseDTO.builder()
                    .response(errorMessage)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        Optional<Producto> productoOpt = productoRepository.findByIdAndActivoTrue(id);

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();

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
        } else {
            return ResponseDTO.builder()
                    .message(Constants.PRODUCTO_ELIMINADO_O_ELIMINADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    public ResponseDTO<Object> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findByActivoTrue();

        return ResponseDTO.builder()
                .response(productos)
                .code(HttpStatus.OK.value())
                .message("Productos obtenidos exitosamente")
                .build();
    }

    public ResponseDTO<Object> obtenerProductoPorId(Long id) {
        Optional<Producto> productoOpt = productoRepository.findByIdAndActivoTrue(id);

        if (productoOpt.isPresent()) {
            return ResponseDTO.builder()
                    .response(productoOpt.get())
                    .code(HttpStatus.OK.value())
                    .message("Producto encontrado exitosamente")
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constants.PRODUCTO_ELIMINADO_O_ELIMINADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    public ResponseDTO<Object> eliminarProductoLogico(Long id) {
        Optional<Producto> productoOpt = productoRepository.findByIdAndActivoTrue(id);

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setActivo(false);
            productoRepository.save(producto);

            return ResponseDTO.builder()
                    .message("Producto eliminado lógicamente")
                    .code(HttpStatus.OK.value())
                    .build();
        } else {
            return ResponseDTO.builder()
                    .message(Constants.PRODUCTO_ELIMINADO_O_ELIMINADO)
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    private Producto convertToEntity(ProductoDto productoDto, CategoriaProducto categoria) {
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setStock(productoDto.getStock());
        producto.setCategoriaProducto(categoria);
        return producto;
    }
}