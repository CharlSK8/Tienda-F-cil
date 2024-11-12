package com.tienda.facil.core.service.producto;

import com.tienda.facil.core.model.producto.ProductoModel;
import com.tienda.facil.core.repository.producto.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    public List<ProductoModel> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Optional<ProductoModel> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    public ProductoModel guardarProducto(ProductoModel producto) {
        return productoRepository.save(producto);
    }

    public ProductoModel actualizarProducto(Long id, ProductoModel producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}