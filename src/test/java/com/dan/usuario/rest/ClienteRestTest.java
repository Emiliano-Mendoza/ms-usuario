package com.dan.usuario.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoObra;
import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.domain.Usuario;
import com.dan.usuario.repository.ClienteRepository;
import com.dan.usuario.service.ClienteService;

@SpringBootTest
public class ClienteRestTest {
	
	@Autowired
	ClienteService clienteService;
	
	@MockBean
	ClienteRepository clienteRepo;
	
	Cliente cliente1;
	Cliente cliente2;
	Cliente cliente3;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cliente1 = new Cliente();
		cliente2 = new Cliente();
		cliente3 = new Cliente();
		Obra obra = new Obra();
		TipoObra tipoO = new TipoObra();
		obra.setTipo(tipoO);
		TipoUsuario tipoU = new TipoUsuario();
		Usuario u0 = new Usuario(1,"pol943","aasdads",tipoU);
		Usuario u1 = new Usuario(1,null,"aasdads",tipoU);
		Usuario u2 = new Usuario(1,"emi123",null,tipoU);
		Usuario u3 = new Usuario(1,"leo43","ml322",null);
		
		//Cliente con datos necesarios
		cliente1.setObras(new ArrayList<Obra>());
		cliente1.getObras().add(obra);
		cliente1.setUser(u0);
		
		//Cliente sin obra
		cliente2.setObras(new ArrayList<Obra>());
		cliente2.setUser(u0);
		
		
	}
	
}
