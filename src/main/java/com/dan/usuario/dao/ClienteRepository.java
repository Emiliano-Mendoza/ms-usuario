package com.dan.usuario.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	public Optional<Cliente> findByCuit(String cuit);
	public Optional<Cliente> findByRazonSocial(String razonSocial);
	
}
