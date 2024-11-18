package com.tienda.facil.core.dto.request;

import com.tienda.facil.core.util.enums.EstadoActivo;
import com.tienda.facil.core.util.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarClienteRequestDTO {

    @Schema(description = "Primer nombre del usuario", example = "Carlos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String nombre;
    @Schema(description = "Segundo nombre del usuario", example = "Andres", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String segundoNombre;
    @Schema(description = "Primer apellido del usuario", example = "López", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String apellido;
    @Schema(description = "Segundo apellido del usuario", example = "Correa", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String segundoApellido;
    @Schema(description = "Número de contacto del usuario", example = "3015501212", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^\\d+$", message = "El campo 'contacto' únicamente admite números")
    @Size(min = 10, max = 12, message = "El campo 'contacto' debe tener entre 10 y 12 dígitos")
    private String contacto;
    @Schema(description = "Email del usuario", example = "carlos@gmail.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com)$", message = "El correo debe ser válido y terminar en .com")
    private String email;
    @Schema(description = "Contraseña de ingreso al aplicativo", example = "*******", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluir letras, números y un carácter especial"
    )
    private String password;
    @Schema(description = "Estado en el cual se encuentra el cliente", example = "ACTIVO", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private EstadoActivo activo;
    @Schema(description = "Roles para el cliente", example = "[ADMIN]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<Role> roles;

}
