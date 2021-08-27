package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.dan.usuario.repository.ClienteRepository;
import com.dan.usuario.dao.ClienteRepository;
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
			return clienteRepo.save(c);
		}
		throw new CredencialException("No tiene aprobacion crediticia");	

	}

	@Override
	public List<Cliente> listarClientes() {	
		return clienteRepo.findAll();
	}
	
	@Override
	public Optional<Cliente> buscarPorId(Integer id) { 	
		return clienteRepo.findById(id);
	}

	@Override
	public void eliminarCliente(Integer id) {
		clienteRepo.deleteById(id);	
	}

	@Override
	public Optional<Cliente> findByCuit(String cuit) {
		return clienteRepo.findByCuit(cuit);
	}

	@Override
	public Optional<Cliente> findByRazonSocial(String razonSocial) {
		return clienteRepo.findByRazonSocial(razonSocial);
	}

	@Override
	public boolean existeCliente(Integer id) {
		return clienteRepo.existsById(id);
	}

	@Override
	public Optional<Cliente> findByMail(String mail) {
		// TODO Auto-generated method stub
		return clienteRepo.findByMail(mail);
	}

	@Override
	public Cliente buscarPorUsuario(Integer idUsuario) {
		Cliente cliente = clienteRepo.findAll()
				.stream()
				.filter(c -> c.getUser().getId().equals(idUsuario))
				.findFirst().get()
				;
		
		
		return cliente;
	}
	
}
