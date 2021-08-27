package com.dan.usuario.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dan.usuario.domain.Usuario;
import com.dan.usuario.service.ClienteService;
import com.dan.usuario.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
@Api(value = "UsuarioRest")
public class UsuarioRest {
	
    @Autowired
    private UsuarioService usuarioServ;
	
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un usuario por id")
    public ResponseEntity<Usuario> EmpleadoPorId(@PathVariable Integer id){
        return ResponseEntity.of(usuarioServ.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> todos(){
        return ResponseEntity.ok(usuarioServ.getAllUsuarios());
    }
    
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario nuevo){
    	System.out.println(" crear Usuario "+nuevo);
    	Usuario temp = usuarioServ.createUsuario(nuevo);
      
      try {
      	return ResponseEntity.created(new URI("/api/usuario" + temp.getId())).body(temp);
      	
      }catch(Exception e) {
      	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
    }
    
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Usuario> borrar(@PathVariable Integer id){
    	usuarioServ.deleteUsuarioPorId(id);
        return ResponseEntity.ok().build();
    }
}
