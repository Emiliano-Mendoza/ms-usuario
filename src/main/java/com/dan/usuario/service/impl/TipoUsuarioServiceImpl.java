package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.dao.TipoUsuarioRepository;
import com.dan.usuario.domain.TipoUsuario;
import com.dan.usuario.service.TipoUsuarioService;
@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioService {
	
	@Autowired
	private TipoUsuarioRepository tipoURepo;
	
	@Override
	public TipoUsuario createTipoUsuario(TipoUsuario tipoU) {
		return tipoURepo.save(tipoU);
	}

	@Override
	public List<TipoUsuario> getAllTipoUsuarios() {
		return tipoURepo.findAll();
	}

	@Override
	public void deleteTipoUsuario(TipoUsuario tipoU) {
		tipoURepo.delete(tipoU);
	}

	@Override
	public void deleteTipoUsuarioPorId(Integer id) {
		tipoURepo.deleteById(id);
	}

	@Override
	public Optional<TipoUsuario> findById(Integer id) {
		return tipoURepo.findById(id);
	}

}
