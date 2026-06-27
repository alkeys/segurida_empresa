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

}
