package com.dan.usuario.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
}
