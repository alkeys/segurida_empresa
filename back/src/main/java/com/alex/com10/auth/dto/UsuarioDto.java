package com.alex.com10.auth.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private UUID id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @NotNull(message = "El ID del rol es obligatorio")
    private UUID idRol;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
    
    private OffsetDateTime ultimoLogin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public UsuarioDto(UUID id, String username, String password, UUID idRol, String estado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idRol = idRol;
        this.estado = estado;
    }
    
}
