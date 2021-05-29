package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.dao.UsuarioRepository;
import com.dan.usuario.domain.Usuario;
import com.dan.usuario.service.UsuarioService;
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Override
	public Usuario createUsuario(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioRepo.findAll();
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		usuarioRepo.delete(usuario);
	}

	@Override
	public void deleteUsuarioPorId(Integer id) {
		usuarioRepo.deleteById(id);

	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepo.findById(id);
	}

}
