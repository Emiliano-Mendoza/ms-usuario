package com.dan.usuario.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.TipoObra;

public interface TipoObraRepository extends JpaRepository<TipoObra, Integer> {

}
