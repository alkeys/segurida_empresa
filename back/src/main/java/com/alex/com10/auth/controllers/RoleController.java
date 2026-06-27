package com.alex.com10.auth.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.com10.auth.dto.RoleDto;
import com.alex.com10.auth.models.Role;
import com.alex.com10.auth.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends AbstractCrudController<Role, UUID, RoleDto, RoleService> {

    @Autowired
    public RoleController(RoleService service) {
        super(service);
    }

    @Override
    protected RoleDto toDto(Role entity) {
        if (entity == null) {
            return null;
        }
        return new RoleDto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getEstado()
        );
    }

    @Override
    protected Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        return Role.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .build();
    }
}
