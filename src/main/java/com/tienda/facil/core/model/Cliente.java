package com.tienda.facil.core.model;

import com.tienda.facil.core.util.enums.EstadoActivo;
import com.tienda.facil.core.util.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String segundoNombre;
    private String apellido;
    private String segundoApellido;
    private String contacto;
    private LocalDateTime fechaRegistro;

    @Column(unique=true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Token> tokens;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private EstadoActivo activo = EstadoActivo.ACTIVO;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        roles = Set.of(Role.USER);
    }

}
