package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.TipoUsuario;


public interface TipoUsuarioService {
	
	public TipoUsuario createTipoUsuario(TipoUsuario tipoU);
	public List<TipoUsuario> getAllTipoUsuarios();
	public void deleteTipoUsuario(TipoUsuario tipoU);
	public void deleteTipoUsuarioPorId(Integer id);
	public Optional<TipoUsuario> findById(Integer id);
	
}
