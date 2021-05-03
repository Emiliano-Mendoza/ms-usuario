package com.dan.usuario.rest;


import com.dan.usuario.service.ClienteService;
import com.dan.usuario.domain.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest")
public class ClienteRest {
    
    private static final List<Cliente> listaClientes = new ArrayList<>();
    //private static Integer ID_GEN = 1;

    @Autowired
	private ClienteService clienteServ;
    
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id){
    	
    	Optional<Cliente> cliente = clienteServ.buscarPorId(id);
    	
        return ResponseEntity.of(cliente);
    }
    
    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por cuit")
    public ResponseEntity<Cliente> clientePorCuit(@PathVariable String cuit){

        Optional<Cliente> c =  listaClientes
                .stream()
                .filter(unCli -> unCli.getCuit().equals(cuit))
                .findFirst();
        return ResponseEntity.of(c);
    }
    
    @GetMapping(path = "/razonSocial/{razonSocial}")
    @ApiOperation(value = "Busca un cliente por razonSocial")
    public ResponseEntity<Cliente> clientePorRazonSocial(@PathVariable String razonSocial){

        Optional<Cliente> c =  listaClientes
                .stream()
                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial))
                .findFirst();
        return ResponseEntity.of(c);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> todos(){
    	
    	List<Cliente> clientes = clienteServ.listarClientes();
    	
    	if(clientes.isEmpty()){
    		return ResponseEntity.noContent().build();
    	}
    	else return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente nuevo){
    	
    	if(nuevo.getObras() != null && nuevo.getUser().getUser() != null
    			&& nuevo.getUser().getPassword() != null) {
    		
            OptionalInt indexOpt =   IntStream.range(0, nuevo.getObras().size())
            .filter(i -> nuevo.getObras().get(i).getTipo() == null)
            .findFirst();
    		
            if(!indexOpt.isPresent() ){
            	
            	Cliente creado = null;
            	try {
            		creado = this.clienteServ.crearCliente(nuevo);
            	}
            	catch(Exception e){
            		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            	}       	
            	return ResponseEntity.ok(creado);
            }
            
    	}
    	throw new RuntimeException("Error, campos incompletos");
    	//return ResponseEntity.notFound().build();
    	
    	
    	
    	
    }

    @PutMapping(path = "/id/{id}")
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente nuevo,  @PathVariable Integer id){
        
    	OptionalInt indexOpt =   IntStream.range(0, listaClientes.size())
        .filter(i -> listaClientes.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaClientes.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Cliente> borrar(@PathVariable Integer id){

    	
    	if(this.clienteServ.eliminarCliente(id)){
              return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    	
    }


}