package com.dan.usuario.repository;

import org.springframework.stereotype.Repository;

import com.dan.usuario.domain.Cliente;

import frsf.isi.dan.InMemoryRepository;

@Repository
public class ClienteRepository extends InMemoryRepository<Cliente> {
	
	@Override
	public Integer getId(Cliente entity) {
	return entity.getId();
	}
	
	@Override
	public void setId(Cliente entity, Integer id) {
	entity.setId(id);
}
}