package com.tienda.facil.core.controller;

import com.tienda.facil.core.model.DetallePedidoModel;
import com.tienda.facil.core.service.DetallePedidoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetalltePedidoController {
    private final DetallePedidoService detallePedidoService;

    public DetalltePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedidoModel> obtenerDetalles() {
        return detallePedidoService.obtenerDetalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoModel> obtenerDetalle(@PathVariable Long id) {
        return detallePedidoService.obtenerDetalle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetallePedidoModel guardarDetalle(@RequestBody DetallePedidoModel detallePedido) {
        return detallePedidoService.guardarDetalle(detallePedido);
    }

    @PutMapping("/{id}")
    public DetallePedidoModel actualizarDetalle(@PathVariable Long id, @RequestBody DetallePedidoModel detallePedido) {
        return detallePedidoService.actualizarDetalle(id, detallePedido);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Long id) {
        detallePedidoService.eliminarDetalle(id);
    }
}