package com.alex.com10.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    /*

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo, UsuarioRepository userRepo, InitializerHelper helper) {
        return args -> {
            helper.initialize(roleRepo, userRepo);
        };
    }
}

@org.springframework.stereotype.Component
class InitializerHelper {

    @org.springframework.transaction.annotation.Transactional
    public void initialize(RoleRepository roleRepo, UsuarioRepository userRepo) {
        // Buscar "ADMIN" o "admin" de forma insensible a mayúsculas
        Role adminRole = roleRepo.findAll().stream()
                .filter(r -> r.getNombre().equalsIgnoreCase("ADMIN"))
                .findFirst()
                .orElse(null);

        if (adminRole == null) {
            Role newAdminRole = Role.builder()
                    .nombre("ADMIN")
                    .descripcion("Administrador del sistema")
                    .estado("ACTIVO")
                    .build();
            adminRole = roleRepo.save(newAdminRole);
        }

        Usuario existingUser = userRepo.findAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase("admin"))
                .findFirst()
                .orElse(null);

        if (existingUser == null) {
            Usuario adminUser = Usuario.builder()
                    .username("admin")
                    .password(HashGenerate.hashPassword("admin"))
                    .rol(adminRole)
                    .estado("ACTIVO")
                    .build();
            userRepo.save(adminUser);
            System.out.println(">>> Usuario administrador inicial creado: admin / admin");
        }
    }
        */
}
