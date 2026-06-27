package com.alex.com10.auth.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private UUID id;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    private String nombre;

    private String descripcion;
    private String estado;
    
}
