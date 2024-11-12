package com.tienda.facil.core.controller.producto;

import com.tienda.facil.core.dto.ResponseDTO;
import com.tienda.facil.core.dto.producto.ProductoDto;
import com.tienda.facil.core.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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