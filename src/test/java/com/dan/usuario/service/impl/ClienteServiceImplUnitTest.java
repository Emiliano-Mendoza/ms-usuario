package com.dan.usuario.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoObra;
import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.domain.Usuario;
import com.dan.usuario.exceptions.CredencialException;
import com.dan.usuario.repository.ClienteRepository;
import com.dan.usuario.service.ClienteService;
import com.dan.usuario.service.CredencialService;

@SpringBootTest
public class ClienteServiceImplUnitTest {
	
	@Autowired
	ClienteService clienteService;
	
	@MockBean
	ClienteRepository clienteRepo;
	@MockBean
	CredencialService credencialService;
	
	Cliente cliente1;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cliente1 = new Cliente();
	
	}
	
	@Test
	void testCrearClienteConCrediticiaBCRA1() throws CredencialException {
		
		when(credencialService.situacionCrediticiaBCRA(any(Cliente.class))).thenReturn(1);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		
		clienteService.crearCliente(cliente1);
		verify(clienteRepo,times(1)).save(cliente1);
	}
	
	@Test
	void testCrearClienteConCrediticiaBCRA2() throws CredencialException {
		
		when(credencialService.situacionCrediticiaBCRA(any(Cliente.class))).thenReturn(2);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		
		clienteService.crearCliente(cliente1);
		verify(clienteRepo,times(1)).save(cliente1);
	}
	
	@Test 
	void testCrearClienteConCrediticiaBCRA3o4() throws CredencialException{
		
		when(credencialService.situacionCrediticiaBCRA(any(Cliente.class))).thenReturn(3);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		
		Exception exception = assertThrows(CredencialException.class, () -> {
			clienteService.crearCliente(cliente1);
	    });
		
		String expectedMessage = "No tiene aprobacion crediticia";
	    String actualMessage = exception.getMessage();
		
	    assertTrue(actualMessage.contains(expectedMessage));
	}
}
