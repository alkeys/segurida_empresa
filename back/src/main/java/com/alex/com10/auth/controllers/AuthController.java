package com.alex.com10.auth.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.alex.com10.auth.dto.LoginRequestDto;
import com.alex.com10.auth.dto.AuthResponseDto;
import com.alex.com10.auth.models.Usuario;
import com.alex.com10.auth.repository.UsuarioRepository;
import com.alex.com10.utils.HashGenerate;
import com.alex.com10.utils.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));

        // Comparar contraseña plana con el hash de la BD
        if (!HashGenerate.checkPassword(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

        // Determinar rol del usuario
        String roleName = usuario.getRol() != null ? usuario.getRol().getNombre().toUpperCase() : "CLIENTE";

        // Generar token JWT
        String token = jwtService.generateToken(usuario.getUsername(), List.of(roleName));

        return ResponseEntity.ok(new AuthResponseDto(token, usuario.getUsername(), roleName));
    }
}
