package com.dan.usuario.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.dan.usuario.dao.ClienteRepository;
import com.dan.usuario.dao.TipoObraRepository;
import com.dan.usuario.service.ClienteService;
import com.dan.usuario.service.TipoObraService;
import com.dan.usuario.DanUsuarioTest;
import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoObra;

@SpringBootTest( 
		classes = DanUsuarioTest.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile("testing")
public class ObraRestTest {
	@LocalServerPort
	String puerto;
	  
	private final String urlServer= "http://localhost";
	private final String apiObra = "api/obra";
    int randomServerPort;
	
    private RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
	ClienteRepository clienteRepo;
    @Autowired
    TipoObraRepository tipoRepo;
    
	@MockBean
	ClienteService clienteServ;
	@MockBean
	TipoObraService tipoServ;
    
    Obra obra1;
    Cliente cliente1;
    
 	@BeforeEach
 	void setUp() {	
 		obra1 = new Obra();
 		cliente1 = new Cliente();
 		obra1.setDescripcion("desc");
 		obra1.setDireccion("dir");
 		obra1.setLatitud((float) 10.3);
 		obra1.setLongitud((float) 43.1);
 		obra1.setSuperficie(32);
 		obra1.setTipo(tipoRepo.save(new TipoObra(1)));
 		
 		//El cliente siempre va a aparecer null para evitar la recursividad infinita
 		obra1.setCliente(clienteRepo.save(new Cliente()));
 		
 	}
    
 	@Test
    void deberiaAceptarObra() {
 		String server = urlServer+":"+puerto+"/"+apiObra;
		System.out.println("SERVER "+server);
				
		Optional<TipoObra> opTO = Optional.ofNullable(obra1.getTipo());
		when(tipoServ.findById(anyInt())).thenReturn(opTO);
		
		HttpEntity<Obra> requestObra = new HttpEntity<>(obra1);
		ResponseEntity<Obra> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestObra , Obra.class);
		
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));
 	}
 	
 	@Test
    void noDeberiaAceptarPorCamposIncompletos() {
 		String server = urlServer+":"+puerto+"/"+apiObra;
		System.out.println("SERVER "+server);
		obra1.setDescripcion(null);
		
		
		Optional<TipoObra> opTO = Optional.ofNullable(obra1.getTipo());
		when(tipoServ.findById(anyInt())).thenReturn(opTO);
		
		HttpEntity<Obra> requestObra = new HttpEntity<>(obra1);
		Throwable ex = assertThrows( 
			      HttpClientErrorException.class, 
			      () -> {ResponseEntity<Obra> respuesta = restTemplate.exchange(server, HttpMethod.POST, requestObra , Obra.class);
			      assertTrue(respuesta.getStatusCode().equals(HttpStatus.CREATED));});
			      
		System.out.println(ex.getMessage());
		
		assertTrue(ex.getMessage().startsWith("400"));
 	}
 		
 	
 	
 	
}
