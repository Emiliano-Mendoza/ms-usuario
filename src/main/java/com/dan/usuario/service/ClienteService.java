package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.Obra;
import com.dan.usuario.domain.Cliente;

public interface ClienteService {
	
	public Cliente crearCliente(Cliente c);
	public List<Cliente> listarClientes ();
	public Optional<Cliente> buscarPorId(Integer id);
	public Boolean eliminarCliente(Integer id);
	public Integer situacionCrediticiaBCRA(Cliente c);
}
