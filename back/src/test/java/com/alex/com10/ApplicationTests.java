package com.alex.com10;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.alex.com10.auth.dto.UsuarioDto;
import com.alex.com10.auth.models.Usuario;
import com.alex.com10.auth.repository.RoleRepository;
import com.alex.com10.auth.services.UsuarioService;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	RoleRepository rolRepository;

	@Autowired
	UsuarioService usuarioService;

	@Test
	void crearUsuarioAdmin() {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setUsername("admin");
		usuarioDto.setPassword("admin");
		usuarioDto.setIdRol(UUID.fromString("a6ebfc80-c552-4ffe-b360-026119880cdc"));
		usuarioDto.setEstado("ACTIVO");
		
		Usuario usuario = Usuario.builder()
				.username(usuarioDto.getUsername())
				.password(usuarioDto.getPassword())
				.rol(rolRepository.findById(usuarioDto.getIdRol()).orElseThrow())
				.estado(usuarioDto.getEstado())
				.build();
		System.out.println(usuarioService.save(usuario));
	}

}
