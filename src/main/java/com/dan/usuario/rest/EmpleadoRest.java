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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dan.usuario.domain.Empleado;
import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.domain.Usuario;
import com.dan.usuario.service.EmpleadoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest")
public class EmpleadoRest {
    
    @Autowired
    private EmpleadoService empleadoServ;
    
    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Busca un empleado por id")
    public ResponseEntity<Empleado> EmpleadoPorId(@PathVariable Integer id){
        return ResponseEntity.of(empleadoServ.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Empleado>> todos(){
        return ResponseEntity.ok(empleadoServ.getAllEmpleados());
    }
    
    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado nuevo){
    	System.out.println(" crear empleado "+nuevo);
    	
    	
    	if(nuevo.getMail()!=null) {
    		// Patrón para validar el email
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

            Matcher mather = pattern.matcher(nuevo.getMail());
            
            if (mather.find() == true) {
            	
            	if(nuevo.getUser()==null) {
        			Usuario usr = new Usuario();
        			usr.setPassword("1234");
        			usr.setUser(nuevo.getMail());
        			usr.setTipoUsuario(new TipoUsuario(2,"Vendedor"));
        			nuevo.setUser(usr);
        		}
            	
            	Empleado temp = empleadoServ.createEmpleado(nuevo);
            	
                try {
                  	return ResponseEntity.created(new URI("/api/empleado" + temp.getId())).body(temp);
                  }catch(Exception e) {
                  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                  }
            }
            else throw new RuntimeException("--- Error, mail invalido --- ");
    	}
    	else throw new RuntimeException("--- Error, mail inexistente --- ");
    }
    
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Empleado> borrar(@PathVariable Integer id){
        empleadoServ.deleteEmpleadoPorId(id);
        return ResponseEntity.ok().build();
    }
    
    
//  @PutMapping(path = "/id/{id}")
//  @ApiOperation(value = "Actualiza un empleado")
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = "Actualizado correctamente"),
//      @ApiResponse(code = 401, message = "No autorizado"),
//      @ApiResponse(code = 403, message = "Prohibido"),
//      @ApiResponse(code = 404, message = "El ID no existe")
//  })
//  public ResponseEntity<Empleado> actualizar(@RequestBody Empleado nuevo,  @PathVariable Integer id){
//      OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
//      .filter(i -> listaEmpleados.get(i).getId().equals(id))
//      .findFirst();
//
//      if(indexOpt.isPresent()){
//          listaEmpleados.set(indexOpt.getAsInt(), nuevo);
//          return ResponseEntity.ok(nuevo);
//      } else {
//          return ResponseEntity.notFound().build();
//      }
//  }
    
}
