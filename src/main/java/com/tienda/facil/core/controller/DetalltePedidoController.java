package com.tienda.facil.core.controller;

import com.tienda.facil.core.model.DetallePedido;
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
    public List<DetallePedido> obtenerDetalles() {
        return detallePedidoService.obtenerDetalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerDetalle(@PathVariable Long id) {
        return detallePedidoService.obtenerDetalle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetallePedido guardarDetalle(@RequestBody DetallePedido detallePedido) {
        return detallePedidoService.guardarDetalle(detallePedido);
    }

    @PutMapping("/{id}")
    public DetallePedido actualizarDetalle(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        return detallePedidoService.actualizarDetalle(id, detallePedido);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Long id) {
        detallePedidoService.eliminarDetalle(id);
    }
}