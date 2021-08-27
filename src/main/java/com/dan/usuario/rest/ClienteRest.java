package com.dan.usuario.rest;


import com.dan.usuario.service.ClienteService;
import com.dan.usuario.service.ObraService;
import com.dan.usuario.service.UsuarioService;
import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.domain.Usuario;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest")
public class ClienteRest {

    @Autowired
	private ClienteService clienteServ;
    @Autowired
    private ObraService obraServ;
    @Autowired
    private UsuarioService usuarioServ;
    
    
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id){
    	return ResponseEntity.of(clienteServ.buscarPorId(id));
    }
    
    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por cuit")
    public ResponseEntity<Cliente> clientePorCuit(@PathVariable String cuit){
        return ResponseEntity.of(clienteServ.findByCuit(cuit));
    }
    
    @GetMapping(path = "/razonSocial/{razonSocial}")
    @ApiOperation(value = "Busca un cliente por razonSocial")
    public ResponseEntity<Cliente> clientePorRazonSocial(@PathVariable String razonSocial){
        return ResponseEntity.of(clienteServ.findByRazonSocial(razonSocial));
    }
    
    @GetMapping(path = "/mail/{mail}")
    @ApiOperation(value = "Busca un cliente por mail")
    public ResponseEntity<Cliente> clientePorMail(@PathVariable String mail){
        return ResponseEntity.of(clienteServ.findByMail(mail));
    }
    
    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Cliente asociado a una obra")
    public ResponseEntity<Cliente> clientePorIdObra(@PathVariable Integer idObra){
    	Optional<Obra> obra = obraServ.findById(idObra);
    	return ResponseEntity.of(clienteServ.buscarPorId(obra.get().getCliente().getId()));
    }
    
    @GetMapping(path = "/usuario/{idUsuario}")
    @ApiOperation(value = "Cliente asociado a un usuario")
    public ResponseEntity<Cliente> clientePorIdUsuario(@PathVariable Integer idUsuario){
    	//Optional<Usuario> usuario = usuarioServ.findById(idUsuario);
    	Cliente cliente = clienteServ.buscarPorUsuario(idUsuario);
    	
    	if(cliente != null) {
    		return ResponseEntity.ok(cliente);
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> todos(){
    	 return ResponseEntity.ok(clienteServ.listarClientes());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente nuevo) throws Exception{
    	
    	if(nuevo.getObras() != null && !nuevo.getObras().isEmpty() ) {
    		
    		List<Obra> obras = nuevo.getObras();
    		for(int i=0; i<obras.size();i++) {
    			if(obras.get(i).getDescripcion()==null || obras.get(i).getDireccion()==null
    					|| obras.get(i).getLatitud()==null || obras.get(i).getLongitud()==null
    					|| obras.get(i).getSuperficie()==null || obras.get(i).getTipo()==null) {
    				return ResponseEntity.badRequest().body(("Campos de obra incompletos"));
    			}
    		}
    		
    		Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(nuevo.getMail());
            
            if (mather.find() == true) {
        		
        		if(nuevo.getUser()==null) {
        			Usuario usr = new Usuario();
        			usr.setPassword("1234");
        			usr.setUser(nuevo.getMail());
        			usr.setTipoUsuario(new TipoUsuario(1,"Cliente"));
        			nuevo.setUser(usr);
        		}
        		
        		Cliente temp = clienteServ.crearCliente(nuevo);
        		
        		for(int i=0; i<obras.size();i++) {
        			obras.get(i).setCliente(temp);
        			obraServ.createObra(obras.get(i));
        		}
        				
            	try {
                	return ResponseEntity.created(new URI("/api/cliente" + temp.getId())).body(temp);
                	
                }catch(Exception e) {
                	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            else return ResponseEntity.badRequest().body(("Error, mail invalido"));
        	
            	        
    	}
    	else return ResponseEntity.badRequest().body(("Error, cliente sin obras"));
    	
    }

    @PutMapping
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente nuevo){
        
        if(clienteServ.existeCliente(nuevo.getId())){
        	Cliente cliente = clienteServ.buscarPorId(nuevo.getId()).get();
        	
        	if(nuevo.getCuit()!=null) cliente.setCuit(nuevo.getCuit());
        	if(nuevo.getRazonSocial()!=null) cliente.setRazonSocial(nuevo.getRazonSocial());
        	if(nuevo.getMail()!=null) cliente.setMail(nuevo.getMail());
        	if(nuevo.getMaxCuentaCorriente()!=null) cliente.setMaxCuentaCorriente(nuevo.getMaxCuentaCorriente());
        	if(nuevo.getHabilitadoOnline()!=null) cliente.setHabilitadoOnline(nuevo.getHabilitadoOnline());
        	//if(nuevo.getObras()!=null) cliente.setObras(nuevo.getObras());
        	//if(nuevo.getUser()!=null) cliente.setUser(nuevo.getUser());
        	
            try {
            	//Si se llama a crearCliente con el mismo id se reemplaza en la BD
				return ResponseEntity.ok(clienteServ.crearCliente(cliente));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Cliente> borrar(@PathVariable Integer id){
    	clienteServ.eliminarCliente(id);
    	return ResponseEntity.ok().build();
    }


}