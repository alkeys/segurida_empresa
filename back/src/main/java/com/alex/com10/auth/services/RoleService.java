package com.alex.com10.auth.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alex.com10.auth.models.Role;
import com.alex.com10.auth.repository.RoleRepository;

@Service
public class RoleService extends AbstractCrudService<Role, UUID, RoleRepository> {

    @Autowired
    public RoleService(RoleRepository repository) {
        super(repository);
    }
    
}
