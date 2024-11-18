package com.tienda.facil.core.controller.producto;

import com.tienda.facil.core.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tienda/facil/api/v1/productos")
@Tag(name = "ProductoController", description = "Gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


}