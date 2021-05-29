package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.exceptions.CredencialException;
import com.dan.usuario.domain.Cliente;
import com.dan.usuario.domain.Obra;

public interface ClienteService {
	
	public Cliente crearCliente(Cliente c) throws CredencialException;
	public List<Cliente> listarClientes();
	public Optional<Cliente> buscarPorId(Integer id);
	public void eliminarCliente(Integer id);
	public Optional<Cliente> findByCuit(String cuit);
	public Optional<Cliente> findByRazonSocial(String razonSocial);
	public boolean existeCliente(Integer id);
}
