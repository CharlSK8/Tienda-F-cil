package com.tienda.facil.core.config;

import com.tienda.facil.core.model.Cliente;
import com.tienda.facil.core.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ClienteRepository clienteRepository;

    /**
     * Proporciona una implementación de {@link UserDetailsService} que se utiliza para cargar los detalles
     * del usuario durante el proceso de autenticación. Este método se implementa para definir
     * la forma en que se obtienen los datos del usuario desde una base de datos.
     *
     * @return Una instancia de {@link UserDetailsService} que contiene la lógica de carga de usuarios.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            final Cliente cliente = clienteRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword())
                    .authorities(
                            cliente.getRoles().stream()
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                                    .toList()
                    )
                    .build();
        };
    }

    /**
     * Configura y proporciona una instancia de {@link AuthenticationProvider} para gestionar la autenticación
     * de usuarios en la aplicación. En este caso, se utiliza una implementación de {@link DaoAuthenticationProvider}
     * que verifica las credenciales del usuario a través de un servicio de detalles de usuario y un codificador de contraseñas.
     *
     * <p>El método realiza las siguientes configuraciones:</p>
     * <ul>
     *   <li>Configura el {@link UserDetailsService} para cargar los datos del usuario.</li>
     *   <li>Configura el {@link PasswordEncoder} para codificar y verificar las contraseñas.</li>
     * </ul>
     *
     * @return Una instancia configurada de {@link DaoAuthenticationProvider}, utilizada para validar las credenciales de los usuarios.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Proporciona una instancia de {@link AuthenticationManager} configurada a partir de la
     * configuración de autenticación actual de la aplicación. Este método permite inyectar
     * el `AuthenticationManager` en otros componentes de seguridad.
     *
     * @param authenticationConfiguration La configuración de autenticación utilizada para obtener
     *                                     el {@link AuthenticationManager}.
     * @return Una instancia de {@link AuthenticationManager} obtenida de la configuración de autenticación.
     * @throws Exception Si ocurre un error al obtener el {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Proporciona una implementación de {@link PasswordEncoder} utilizando {@link BCryptPasswordEncoder}.
     * El codificador BCrypt se utiliza para proteger las contraseñas mediante hashing seguro.
     *
     * @return Una instancia de {@link BCryptPasswordEncoder} que se utiliza para codificar contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
