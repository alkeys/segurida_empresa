package com.alex.com10.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alex.com10.auth.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    //cargar usuarios porque es lazy falla
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
    


}
