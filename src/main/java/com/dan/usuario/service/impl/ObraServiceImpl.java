package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.dao.ObraRepository;
import com.dan.usuario.domain.Obra;
import com.dan.usuario.service.ObraService;

@Service
public class ObraServiceImpl implements ObraService{
	
	@Autowired
	private ObraRepository obraRepo;
	
	@Override
	public Obra createObra(Obra obra) {
		return obraRepo.save(obra);
	}

	@Override
	public List<Obra> getAllObras() {
		return obraRepo.findAll();
	}

	@Override
	public void deleteObra(Obra obra) {
		obraRepo.delete(obra);
	}

	@Override
	public void deleteObraPorId(Integer id) {
		obraRepo.deleteById(id);
		
	}

	@Override
	public Optional<Obra> findById(Integer id) {
		return obraRepo.findById(id);
	}


	@Override
	public Optional<Obra> findByTipo(String tipo) {
		return obraRepo.findByTipo(tipo);
	}

	@Override
	public boolean existObra(Integer id) {
		return obraRepo.existsById(id);
	}


}
