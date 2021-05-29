package com.dan.usuario.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {

}
