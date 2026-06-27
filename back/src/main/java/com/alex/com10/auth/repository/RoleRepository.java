package com.alex.com10.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alex.com10.auth.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByNombre(String nombre);
    Boolean existsByNombre(String nombre);

    @org.springframework.data.jpa.repository.Query("SELECT r.id FROM Role r WHERE r.nombre = :nombre")
    Optional<UUID> findIdByNombre(String nombre);
    
}
