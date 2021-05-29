package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.TipoObra;

public interface TipoObraService {
	
	public TipoObra createTipoObra(TipoObra tipo);
	public List<TipoObra> getAllTipoObra();
	public void deleteTipoObra(TipoObra tipo);
	public void deleteTipoObraPorId(Integer id);
	public Optional<TipoObra> findById(Integer id);
	
}
