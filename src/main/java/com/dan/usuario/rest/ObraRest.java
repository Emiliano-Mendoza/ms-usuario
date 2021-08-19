package com.dan.usuario.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.TipoObra;
import com.dan.usuario.service.ObraService;
import com.dan.usuario.service.TipoObraService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/obra")
@Api(value = "ObraRest")
public class ObraRest {
	
	@Autowired
    private ObraService obraServ;
    @Autowired
	private TipoObraService tipoServ;
	
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca una obra por id")
    public ResponseEntity<Obra> ObraPorId(@PathVariable Integer id){
        return ResponseEntity.of(obraServ.findById(id));
    }
    
    @GetMapping(path = "/cliente/{IdCliente}")
    @ApiOperation(value = "Busca una obra por id de cliente")
    public ResponseEntity<List<Obra>> ObraPorCliente(@PathVariable Integer IdCliente){
    	List<Obra> listaObras = obraServ.getAllObras();
    	List<Obra> aux = new ArrayList<>();
    	for(int i=0;i<listaObras.size();i++) {
    		if(listaObras.get(i).getCliente().getId()==IdCliente) aux.add(listaObras.get(i));
    	}
        return ResponseEntity.ok(aux);
    }
    @GetMapping(path = "/cliente/{IdCliente}/tipoObra/{tipoObra}")
    @ApiOperation(value = "Busca una obra por id de cliente y el tipo de obra")
    public ResponseEntity<List<Obra>> ObraPorClienteYTipo(@PathVariable Integer IdCliente, @PathVariable String tipoObra){
    	List<Obra> listaObras = obraServ.getAllObras();
    	List<Obra> aux = new ArrayList<>();
    	for(int i=0;i<listaObras.size();i++) {
    		if(listaObras.get(i).getCliente().getId()==IdCliente && listaObras.get(i).getTipo().getDescripcion().equals(tipoObra)) 
    			aux.add(listaObras.get(i));
    	}
        return ResponseEntity.ok(aux);
    }
    
    
    @GetMapping(path = "/tipoObra/{tipoObra}")
    @ApiOperation(value = "Busca obras por tipo de obra")
    public ResponseEntity<List<Obra>> ObraPorTipoObra(@PathVariable String tipoObra){
    	List<Obra> listaObras = obraServ.getAllObras();
    	List<Obra> aux = new ArrayList<>();
    	
    	for(int i=0;i<listaObras.size();i++) {
    		if(listaObras.get(i).getTipo().getDescripcion().equals(tipoObra)) 
    			aux.add(listaObras.get(i));
    	}
        return ResponseEntity.ok(aux);
    }
    
    @GetMapping
    public ResponseEntity<List<Obra>> todos(){
        return ResponseEntity.ok(obraServ.getAllObras());
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Obra nuevo){
    	System.out.println(" crear obra "+nuevo);
    	
    	//verifico que esten completos los campos de la obra
        if(/*nuevo.getCliente()==null ||*/ nuevo.getDescripcion()==null || nuevo.getDireccion()==null
        		|| nuevo.getLatitud()==null || nuevo.getLongitud()==null || nuevo.getSuperficie()==null
        		|| nuevo.getTipo()==null  || nuevo.getTipo().getId()==null) {
        	return ResponseEntity.badRequest().body(("Campos de obra incompletos"));
        }
        else {
        	Optional<TipoObra> tipo = tipoServ.findById(nuevo.getTipo().getId());
        	nuevo.setTipo(tipo.get());
        	Obra temp = obraServ.createObra(nuevo);

        	try {
            	return ResponseEntity.created(new URI("/api/obra" + temp.getId())).body(temp);
            	
            }catch(Exception e) {
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }	
    }
    
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Obra> borrar(@PathVariable Integer id){
        obraServ.deleteObraPorId(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping
    @ApiOperation(value = "Actualiza una obra")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo){

    	if(obraServ.existObra(nuevo.getId())) {
    		Obra obra = obraServ.findById(nuevo.getId()).get();
    		
    		if(nuevo.getDescripcion()!=null) obra.setDescripcion(nuevo.getDescripcion());
    		if(nuevo.getDireccion()!=null) obra.setDireccion(nuevo.getDireccion());
    		if(nuevo.getLatitud()!=null) obra.setLatitud(nuevo.getLatitud());
    		if(nuevo.getLongitud()!=null) obra.setLongitud(nuevo.getLongitud());
    		if(nuevo.getSuperficie()!=null) obra.setSuperficie(nuevo.getSuperficie());
    		if(nuevo.getTipo()!=null) obra.setTipo(nuevo.getTipo());
    		
            try {
				return ResponseEntity.ok(obraServ.createObra(obra));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 
}
