package com.alex.com10.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final List<RouteRule> routeRules;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Autowired
    public SecurityConfig(List<RouteRule> routeRules, JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.routeRules = routeRules;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
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
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .bearerTokenResolver(request -> {
                            // 1. Intentar extraer del header estándar
                            String headerToken = request.getHeader("Authorization");
                            if (headerToken != null && headerToken.startsWith("Bearer ")) {
                                return headerToken.substring(7);
                            }
                            // 2. Extraer del Cookie header
                            if (request.getCookies() != null) {
                                for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                                    if ("jwt".equals(cookie.getName())) {
                                        return cookie.getValue();
                                    }
                                }
                            }
                            return null;
                        })
                ) // Servidor de Recursos JWT
                .httpBasic(httpBasic -> httpBasic.disable()) // Deshabilitar httpBasic
                .formLogin(formLogin -> formLogin.disable()); // Deshabilitar formLogin

        return http.build();
    }
}