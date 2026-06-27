package com.alex.com10.auth.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.com10.auth.dto.UsuarioDto;
import com.alex.com10.auth.models.Role;
import com.alex.com10.auth.models.Usuario;
import com.alex.com10.auth.services.RoleService;
import com.alex.com10.auth.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends AbstractCrudController<Usuario, UUID, UsuarioDto, UsuarioService> {

    private final RoleService roleService;

    @Autowired
    public UsuarioController(UsuarioService service, RoleService roleService) {
        super(service);
        this.roleService = roleService;
    }

    @Override
    protected UsuarioDto toDto(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return new UsuarioDto(
                entity.getId(),
                entity.getUsername(),
                null, // No exponer la contraseña hash por seguridad
                entity.getRol() != null ? entity.getRol().getId() : null,
                entity.getEstado(),
                entity.getUltimoLogin(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected Usuario toEntity(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }
        Role role = null;
        if (dto.getIdRol() != null) {
            role = roleService.findById(dto.getIdRol())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Role not found with ID: " + dto.getIdRol()));
        }
        return Usuario.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .rol(role)
                .estado(dto.getEstado())
                .ultimoLogin(dto.getUltimoLogin())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
