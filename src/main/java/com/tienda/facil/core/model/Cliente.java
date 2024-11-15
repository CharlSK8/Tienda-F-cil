package com.tienda.facil.core.model;

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
    private boolean activo = true;

    @Column(unique=true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Token> tokens;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    //TODO: Relacion con pedidos

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        activo = true;
        roles = Set.of(Role.USER);
    }

}
