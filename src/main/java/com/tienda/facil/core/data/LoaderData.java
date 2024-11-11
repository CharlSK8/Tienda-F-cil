package com.tienda.facil.core.data;

import com.tienda.facil.core.model.ClienteModel;
import com.tienda.facil.core.model.PedidoModel;
import com.tienda.facil.core.model.PrioridadModel;
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
 * Clase encargada de cargar datos iniciales en la base de datos al inicio de la aplicación.
 * Implementa la interfaz {@link CommandLineRunner}, lo que permite que su método run() se ejecute
 * automáticamente cuando la aplicación Spring Boot se inicia.
 */
@Component
public class LoaderData implements CommandLineRunner {

    private final SecureRandom random = new SecureRandom();

    private final ClienteRepository clienteRepository;

    private final PrioridadRepository prioridadRepository;

    private final PedidoRepository pedidoRepository;

    public LoaderData(ClienteRepository clienteRepository, PrioridadRepository prioridadRepository, PedidoRepository pedidoRepository) {

        this.clienteRepository = clienteRepository;
        this.prioridadRepository = prioridadRepository;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Método que carga datos iniciales en la base de datos. Se ejecuta automáticamente al inicio de la aplicación.
     * Aquí se crean entidades de ejemplo (Cliente, Prioridad y Pedido) si no existen en la base de datos.
     *
     * @param args argumentos de la línea de comandos
     * @throws Exception si ocurre algún error durante la carga de datos
     */
    @Override
    public void run(String... args) throws Exception {

        // 1. Comprobar si el cliente ya existe en la base de datos mediante su email
        Optional<ClienteModel> clienteOpt = clienteRepository.findByEmail("juan@example.com");
        ClienteModel cliente;

        if (clienteOpt.isEmpty()) {
            // Crear un nuevo cliente si no existe
            cliente = new ClienteModel();
            cliente.setNombre("Juan");
            cliente.setSegundoNombre("Carlos");
            cliente.setApellido("Pérez");
            cliente.setSegundoApellido("Gómez");
            cliente.setTelefono("123456789");
            cliente.setEmail("juan@example.com");
            cliente.setFechaRegistro(new Date());
            cliente.setActivo(EstadoActivo.ACTIVO);
            cliente = clienteRepository.save(cliente); // Guardar el cliente en la base de datos
        } else {
            cliente = clienteOpt.get(); // Usar el cliente existente si ya está en la base de datos
        }

        // 2. Comprobar si la prioridad ya existe en la base de datos por su nombre
        Optional<PrioridadModel> prioridadOpt = prioridadRepository.findByNombre(PrioridadPedido.ALTA);
        PrioridadModel prioridad;

        if (prioridadOpt.isEmpty()) {
            // Crear una nueva prioridad si no existe
            prioridad = new PrioridadModel();
            prioridad.setNombre(PrioridadPedido.ALTA);
            prioridad = prioridadRepository.save(prioridad); // Guardar la prioridad en la base de datos
        } else {
            prioridad = prioridadOpt.get(); // Usar la prioridad existente si ya está en la base de datos
        }

        // 3. Comprobar si ya existe un pedido con este cliente y esta prioridad
        if (pedidoRepository.findFirstByClienteAndPrioridad(cliente, prioridad).isEmpty()) {
            // Crear un nuevo pedido si no existe uno con este cliente y prioridad específicos
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

            pedidoRepository.save(pedido); // Guardar el pedido en la base de datos
        }
    }

    /**
     * Genera un número de seguimiento único y aleatorio para los pedidos, con un prefijo y una longitud de 10 dígitos.
     *
     * @return un número de seguimiento generado aleatoriamente
     */
    private String generarNumeroSeguimiento() {
        String prefijo = "TF-";
        int numeroAleatorio = random.nextInt(1_000_000_000); // Generar un número aleatorio de 9 dígitos
        return prefijo + String.format("%010d", numeroAleatorio); // Formatear a 10 dígitos incluyendo el prefijo
    }
}