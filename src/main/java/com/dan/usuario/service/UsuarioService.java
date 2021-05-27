package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.Usuario;


public interface UsuarioService {
	
	public Usuario createUsuario(Usuario usuario);
	public List<Usuario> getAllUsuarios();
	public void deleteUsuario(Usuario usuario);
	public void deleteUsuarioPorId(Integer id);
	public Optional<Usuario> findById(Integer id);
	
}
