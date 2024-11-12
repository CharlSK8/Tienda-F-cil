package com.tienda.facil.core.service;

import com.tienda.facil.core.model.DetallePedidoModel;
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

    public List<DetallePedidoModel> obtenerDetalles() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedidoModel> obtenerDetalle(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedidoModel guardarDetalle(DetallePedidoModel detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public DetallePedidoModel actualizarDetalle(Long id, DetallePedidoModel detallePedido) {
        detallePedido.setId(id);
        return detallePedidoRepository.save(detallePedido);
    }

    public void eliminarDetalle(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}