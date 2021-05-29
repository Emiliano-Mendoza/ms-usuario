package com.dan.usuario.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.Obra;

public interface ObraRepository extends JpaRepository<Obra, Integer> {
	
	public List<Obra> findByCliente(Integer id);
	public Optional<Obra> findByTipo(String tipo);
}
