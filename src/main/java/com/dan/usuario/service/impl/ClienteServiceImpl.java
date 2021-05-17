package com.dan.usuario.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.repository.ClienteRepository;
import com.dan.usuario.domain.Cliente;
import com.dan.usuario.exceptions.CredencialException;
import com.dan.usuario.service.ClienteService;
import com.dan.usuario.service.CredencialService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	//private static final List<Cliente> listaClientes = new ArrayList<>();
	//private static Integer ID_GEN = 1;
	
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	ClienteService clienteServ;
	@Autowired
	CredencialService credencialService;
	
	@Override
	public Cliente crearCliente(Cliente c) throws CredencialException{
		
		if (this.credencialService.situacionCrediticiaBCRA(c) == 1 || this.credencialService.situacionCrediticiaBCRA(c) == 2) {
			return this.clienteRepo.save(c);
		}
		throw new CredencialException("No tiene aprobacion crediticia");	

	}

	@Override
	public List<Cliente> listarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		Iterable<Cliente> i = this.clienteRepo.findAll();
		i.forEach(c -> clientes.add(c));
		
		return clientes;
	}
	
	@Override
	public Optional<Cliente> buscarPorId(Integer id) { 
		
		return this.clienteRepo.findById(id);
	}

	@Override
	public Boolean eliminarCliente(Integer id) {
		
		if(this.clienteRepo.existsById(id)) {
			this.clienteRepo.deleteById(id);
			return true;
		}
		else return false;	
	}
	
		
}
