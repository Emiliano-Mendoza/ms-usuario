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


import com.dan.usuario.domain.TipoObra;
import com.dan.usuario.service.TipoObraService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tipoObra")
@Api(value = "TipoObraRest")
public class TipoObraRest {
	
	@Autowired
    private TipoObraService tipoServ;
	
    @PostMapping
    public ResponseEntity<TipoObra> crear(@RequestBody TipoObra nuevo){
    	System.out.println(" crear obra "+nuevo);
        
    	TipoObra temp = tipoServ.createTipoObra(nuevo);

    	try {
        	return ResponseEntity.created(new URI("/api/tipoObra" + temp.getId())).body(temp);
        	
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<TipoObra>> todos(){
        return ResponseEntity.ok(tipoServ.getAllTipoObra());
    }
    
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un TipoObra por id")
    public ResponseEntity<TipoObra> ObraPorId(@PathVariable Integer id){
        return ResponseEntity.of(tipoServ.findById(id));
    }
    
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<TipoObra> borrar(@PathVariable Integer id){
        tipoServ.deleteTipoObraPorId(id);
        return ResponseEntity.ok().build();
    }
}
