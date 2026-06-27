package com.alex.com10.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final List<RouteRule> routeRules;

    @Autowired
    public SecurityConfig(List<RouteRule> routeRules) {
        this.routeRules = routeRules;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> {
                    // 1. Rutas públicas comunes
                    auth.requestMatchers(
                            "/api/auth/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll();

                    // 2. Cargar dinámicamente el listrouter de RouterConfig
                    for (RouteRule rule : routeRules) {
                        auth.requestMatchers(rule.getPattern())
                            .hasAnyRole(rule.getRoles().toArray(new String[0]));
                    }

                    // 3. Cualquier otra petición debe estar autenticada
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // Servidor de Recursos JWT
                .httpBasic(httpBasic -> httpBasic.disable()) // Deshabilitar httpBasic
                .formLogin(formLogin -> formLogin.disable()); // Deshabilitar formLogin

        return http.build();
    }
}