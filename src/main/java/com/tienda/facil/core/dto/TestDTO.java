package com.tienda.facil.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {


    @Schema(description = "Primer nombre del usuario", example = "Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;
    @Schema(description = "Edad del usuario", example = "18", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El campo 'age' es obligatorio")
    private int age;
}
