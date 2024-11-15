package com.tienda.facil.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @Schema(description = "Email del usuario", example = "carlos@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El campo 'email' es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
    @Schema(description = "Password de ingreso al aplicativo", example = "18", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El campo 'password' es obligatorio")
    private String password;
}
