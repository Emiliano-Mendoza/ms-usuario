package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.Obra;


public interface ObraService {
	
	public Obra createObra(Obra obra);
	public List<Obra> getAllObras();
	public void deleteObra(Obra obra);
	public void deleteObraPorId(Integer id);
	public Optional<Obra> findById(Integer id);
	
	
	public Optional<Obra> findByTipo(String tipo);
	public boolean existObra(Integer id);
}
