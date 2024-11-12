package com.tienda.facil.core.service.producto;
import com.tienda.facil.core.repository.producto.ProductoRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

}