package com.dan.usuario.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import com.dan.usuario.DanUsuarioTest;
import com.dan.usuario.dao.ClienteRepository;
import com.dan.usuario.dao.ObraRepository;
import com.dan.usuario.dao.TipoObraRepository;
import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoObra;



@SpringBootTest( 
		classes = DanUsuarioTest.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile("testing")
public class ClienteRestTest {
	@LocalServerPort
	String puerto;
	  
	private final String urlServer= "http://localhost";
	private final String apiCliente = "api/cliente";
    int randomServerPort;
	
    private RestTemplate restTemplate = new RestTemplate();
    @MockBean
    TipoObraRepository tipoRepo;
    @MockBean
    ObraRepository obraRepo;
    @MockBean
    ClienteRepository clienteRepo;
    
    Cliente cliente1;
    Obra obra1;
    
	@BeforeEach
	void setUp() {	
		cliente1 = new Cliente();
		cliente1.setCuit("4532423");
		cliente1.setHabilitadoOnline(false);
		cliente1.setMail("asdas@gmail.com");
		cliente1.setMaxCuentaCorriente(5000.0);
		cliente1.setRazonSocial("Razon 1");
		
		obra1 = new Obra();
		obra1.setDescripcion("desc");
		obra1.setDireccion("dir");
		obra1.setLatitud((float) 10.3);
		obra1.setLongitud((float) 43.1);
		obra1.setSuperficie(32);
		obra1.setTipo(new TipoObra());

	}
    
    @Test
    void deberiaAceptarCliente() {
    	String server = urlServer+":"+puerto+"/"+apiCliente;
		System.out.println("SERVER "+server);
		
		List<Obra> listaObras = new ArrayList<>();
		listaObras.add(obra1);
		cliente1.setObras(listaObras);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		when(obraRepo.save(any(Obra.class))).thenReturn(obra1);
		when(tipoRepo.save(any(TipoObra.class))).thenReturn(new TipoObra());
		
		HttpEntity<Cliente> requestCliente = new HttpEntity<>(cliente1);
		ResponseEntity<Cliente> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestCliente , Cliente.class);
		
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));
    }
    
    @Test
    void noDeberiaAceptarPorFaltaDeDatosEnObras() {
    	String server = urlServer+":"+puerto+"/"+apiCliente;
		System.out.println("SERVER "+server);
		
		List<Obra> listaObras = new ArrayList<>();
		
		//Pongo descripcion nula 
		obra1.setDescripcion(null);
		listaObras.add(obra1);
		cliente1.setObras(listaObras);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		when(obraRepo.save(any(Obra.class))).thenReturn(obra1);
		when(tipoRepo.save(any(TipoObra.class))).thenReturn(new TipoObra());
		
		HttpEntity<Cliente> requestCliente = new HttpEntity<>(cliente1);
		Throwable ex = assertThrows( 
			      HttpClientErrorException.class, 
			      () -> {ResponseEntity<Cliente> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestCliente , Cliente.class);
			             assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));});
		
		System.out.println(ex.getMessage());
		assertTrue(ex.getMessage().startsWith("400"));
    }
    
    @Test
    void noDeberiaAceptarPorFaltaDeObras() {
    	String server = urlServer+":"+puerto+"/"+apiCliente;
		System.out.println("SERVER "+server);
		
		List<Obra> listaObras = new ArrayList<>();
		//no agrego ninguna obra a la lista
		cliente1.setObras(listaObras);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		when(obraRepo.save(any(Obra.class))).thenReturn(obra1);
		when(tipoRepo.save(any(TipoObra.class))).thenReturn(new TipoObra());
		
		HttpEntity<Cliente> requestCliente = new HttpEntity<>(cliente1);
		Throwable ex = assertThrows( 
			      HttpClientErrorException.class, 
			      () -> {ResponseEntity<Cliente> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestCliente , Cliente.class);
			             assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));});
		
		System.out.println(ex.getMessage());
		assertTrue(ex.getMessage().startsWith("400"));
    }
    
    @Test
    void noDeberiaAceptarPorEmailInvalido() {
    	String server = urlServer+":"+puerto+"/"+apiCliente;
		System.out.println("SERVER "+server);
		cliente1.setMail("asdas-gmail.com");
		List<Obra> listaObras = new ArrayList<>();
		listaObras.add(obra1);
		cliente1.setObras(listaObras);
		
		when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente1);
		when(obraRepo.save(any(Obra.class))).thenReturn(obra1);
		when(tipoRepo.save(any(TipoObra.class))).thenReturn(new TipoObra());
		
		HttpEntity<Cliente> requestCliente = new HttpEntity<>(cliente1);
		Throwable ex = assertThrows( 
			      HttpClientErrorException.class, 
			      () -> {ResponseEntity<Cliente> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestCliente , Cliente.class);
			             assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));});
		
		System.out.println(ex.getMessage());
		assertTrue(ex.getMessage().startsWith("400"));
    }
	
}
