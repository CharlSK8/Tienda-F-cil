package com.tienda.facil.core.service;

import com.tienda.facil.core.model.DetallePedido;
import com.tienda.facil.core.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<DetallePedido> obtenerDetalles() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> obtenerDetalle(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedido guardarDetalle(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public DetallePedido actualizarDetalle(Long id, DetallePedido detallePedido) {
        detallePedido.setId(id);
        return detallePedidoRepository.save(detallePedido);
    }

    public void eliminarDetalle(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}