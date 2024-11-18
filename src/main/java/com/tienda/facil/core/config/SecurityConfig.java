package com.tienda.facil.core.config;

import com.tienda.facil.core.component.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * Este método se encarga de personalizar la configuración de seguridad
     * para las solicitudes HTTP, permitiendo establecer políticas de autenticación,
     * autorización y control de accesos, así como configuraciones relacionadas
     * con la protección CSRF y la gestión de sesiones.
     *
     * @param http una instancia de {@link HttpSecurity} que permite configurar
     *             la seguridad HTTP de la aplicación.
     * @return una instancia de {@link SecurityFilterChain} que define la cadena
     *         de filtros de seguridad a aplicar.
     * @throws Exception si ocurre algún error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/tienda/facil/api/v1/auth/**","/docs/**" ,"/swagger-ui.html", "/docs/swagger-ui/**", "/v3/api-docs/**", "/actuator/health").permitAll()
                        .requestMatchers("/tienda/facil/api/v1/test").hasRole("USER")
                        .requestMatchers("/tienda/facil/api/v1/clientes/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
