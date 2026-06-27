package com.alex.com10.auth.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.com10.auth.models.Usuario;
import com.alex.com10.auth.repository.UsuarioRepository;
import com.alex.com10.utils.HashGenerate;

@Service
public class UsuarioService extends AbstractCrudService<Usuario, UUID, UsuarioRepository> {

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }

    //para guardar la contra cifrada

    @Override
    public Usuario save(Usuario entity) {
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String hash = HashGenerate.hashPassword(entity.getPassword());
        entity.setPassword(hash);
        return repository.save(entity);
    }

    @Override
    public Usuario update(UUID id, Usuario entity) {
        if (id == null || entity == null) {
            throw new IllegalArgumentException("ID and Entity cannot be null");
        }
        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Usuario not found with ID: " + id));

        // Si la contraseña provista es nueva/diferente y no vacía, la hasheamos
        if (entity.getPassword() != null && !entity.getPassword().isEmpty() && !entity.getPassword().equals(existing.getPassword())) {
            entity.setPassword(HashGenerate.hashPassword(entity.getPassword()));
        } else {
            // Si es null o vacía o igual, conservamos la que ya estaba hasheada
            entity.setPassword(existing.getPassword());
        }
        return repository.save(entity);
    }

}
