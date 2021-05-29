package com.dan.usuario.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.service.TipoUsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tipoUsuario")
@Api(value = "TipoUsuarioRest")
public class TipoUsuarioRest {
	
	@Autowired
    private TipoUsuarioService tipoServ;
	
    @PostMapping
    public ResponseEntity<TipoUsuario> crear(@RequestBody TipoUsuario nuevo){
    	System.out.println(" crear TipoUsuario "+ nuevo);
        
    	TipoUsuario temp = tipoServ.createTipoUsuario(nuevo);

    	try {
        	return ResponseEntity.created(new URI("/api/tipoUsuario" + temp.getId())).body(temp);
        	
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<TipoUsuario>> todos(){
        return ResponseEntity.ok(tipoServ.getAllTipoUsuarios());
    }
    
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un TipoUsuario por id")
    public ResponseEntity<TipoUsuario> ObraPorId(@PathVariable Integer id){
        return ResponseEntity.of(tipoServ.findById(id));
    }
    
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<TipoUsuario> borrar(@PathVariable Integer id){
        tipoServ.deleteTipoUsuarioPorId(id);
        return ResponseEntity.ok().build();
    }
}
