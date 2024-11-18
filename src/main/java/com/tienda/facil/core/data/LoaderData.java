package com.tienda.facil.core.data;

import com.tienda.facil.core.model.ClienteModel;
import com.tienda.facil.core.model.PedidoModel;
import com.tienda.facil.core.model.PrioridadModel;
import com.tienda.facil.core.model.producto.ProductoModel;
import com.tienda.facil.core.repository.ClienteRepository;
import com.tienda.facil.core.repository.PedidoRepository;
import com.tienda.facil.core.repository.PrioridadRepository;
import com.tienda.facil.core.utils.enums.EstadoActivo;
import com.tienda.facil.core.utils.enums.EstadoPedido;
import com.tienda.facil.core.utils.enums.MetodoPago;
import com.tienda.facil.core.utils.enums.PrioridadPedido;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

/**
 * Clase que carga datos iniciales en la base de datos al iniciar la aplicación.
 */
@Component
public class LoaderData implements CommandLineRunner {

    private final SecureRandom random = new SecureRandom();

    private final ClienteRepository clienteRepository;
    private final PrioridadRepository prioridadRepository;
    private final PedidoRepository pedidoRepository;

    /**
     * Constructor que inyecta los repositorios necesarios.
     *
     * @param clienteRepository Repositorio de clientes.
     * @param prioridadRepository Repositorio de prioridades.
     * @param pedidoRepository Repositorio de pedidos.
     */
    public LoaderData(ClienteRepository clienteRepository, PrioridadRepository prioridadRepository, PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.prioridadRepository = prioridadRepository;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Método que se ejecuta al iniciar la aplicación y carga datos iniciales.
     *
     * @param args Argumentos de la línea de comandos.
     * @throws Exception Si ocurre un error durante la carga de datos.
     */
    @Override
    public void run(String... args) throws Exception {

    // Buscar cliente por email
    Optional<ClienteModel> clienteOpt = clienteRepository.findByEmail("juan@example.com");
    ClienteModel cliente;

    // Si el cliente no existe, crear uno nuevo
    if (clienteOpt.isEmpty()) {
        cliente = new ClienteModel();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@example.com");
        cliente = clienteRepository.save(cliente);
    } else {
        // Si el cliente existe, usar el existente
        cliente = clienteOpt.get();
    }

    // Buscar prioridad por nombre
    Optional<PrioridadModel> prioridadOpt = prioridadRepository.findByNombre(PrioridadPedido.ALTA);
    PrioridadModel prioridad;

    // Si la prioridad no existe, crear una nueva
    if (prioridadOpt.isEmpty()) {
        prioridad = new PrioridadModel();
        prioridad.setNombre(PrioridadPedido.ALTA);
        prioridad = prioridadRepository.save(prioridad);
    } else {
        // Si la prioridad existe, usar la existente
        prioridad = prioridadOpt.get();
    }

    // Verificar si ya existe un pedido para el cliente y la prioridad especificados
    if (pedidoRepository.findFirstByClienteAndPrioridad(cliente, prioridad).isEmpty()) {
        // Crear un nuevo pedido
        PedidoModel pedido = new PedidoModel();
        pedido.setCliente(cliente);
        pedido.setPrioridad(prioridad);
        pedido.setFechaPedido(new Date());
        pedido.setFechaEntrega(new Date());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setMontoTotal(new BigDecimal("250.75"));
        pedido.setMetodoPago(MetodoPago.TARJETA);
        pedido.setDireccionEnvio("123 Calle Falsa 2");
        pedido.setFechaModificacion(new Date());
        pedido.setNumeroSeguimiento(generarNumeroSeguimiento());

        // Guardar el pedido en la base de datos
        pedidoRepository.save(pedido);
    }
}

    /**
     * Genera un número de seguimiento aleatorio para un pedido.
     *
     * @return El número de seguimiento generado.
     */
    private String generarNumeroSeguimiento() {
        String prefijo = "TF-";
        int numeroAleatorio = random.nextInt(1_000_000_000);
        return prefijo + String.format("%010d", numeroAleatorio);
    }
}