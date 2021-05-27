package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.dao.TipoObraRepository;
import com.dan.usuario.domain.TipoObra;
import com.dan.usuario.service.TipoObraService;
@Service
public class TipoObraServiceImpl implements TipoObraService {
	
	@Autowired
	private TipoObraRepository tipoRepo;
	
	@Override
	public TipoObra createTipoObra(TipoObra tipo) {
		return tipoRepo.save(tipo);
	}

	@Override
	public List<TipoObra> getAllTipoObra() {
		return tipoRepo.findAll();
	}

	@Override
	public void deleteTipoObra(TipoObra tipo) {
		tipoRepo.delete(tipo);

	}

	@Override
	public void deleteTipoObraPorId(Integer id) {
		tipoRepo.deleteById(id);

	}

	@Override
	public Optional<TipoObra> findById(Integer id) {
		return tipoRepo.findById(id);
	}

}
