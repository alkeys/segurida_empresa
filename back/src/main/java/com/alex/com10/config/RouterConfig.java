package com.alex.com10.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public List<RouteRule> routeRules() {
        return List.of(
            // --- Rutas de Clientes ---
            // Accesible por CLIENTE, SUPERVISOR y ADMIN
            new RouteRule("/api/clientes/**", List.of("CLIENTE", "SUPERVISOR", "ADMIN")),

            // --- Rutas de Supervisor ---
            // Accesible por SUPERVISOR y ADMIN
            new RouteRule("/api/supervisor/**", List.of("SUPERVISOR", "ADMIN")),

            // --- Rutas de Recursos Humanos ---
            // Accesible por RH y ADMIN
            new RouteRule("/api/rh/**", List.of("RH", "ADMIN")),

            // --- Rutas de Administración general ---
            // Accesible únicamente por ADMIN
            new RouteRule("/api/usuarios/**", List.of("ADMIN")),
            new RouteRule("/api/roles/**", List.of("ADMIN")),
            new RouteRule("/api/admin/**", List.of("ADMIN"))
            
            // --- Para agregar más roles/rutas en el futuro, agrégalos aquí ---
            // ejemplo: new RouteRule("/api/rhyotros/**", List.of("RH", "OTRO_ROL", "ADMIN"))
        );
    }
}
