package com.dan.usuario.domain;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="USR_TIPO_USUARIO", schema="MS_USR",
		uniqueConstraints =	{@UniqueConstraint(columnNames= {"TIPO"})})
public class TipoUsuario {
	
	@Id
	private Integer id;
	private String tipo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public TipoUsuario(String tipo) {
		super();
		this.tipo = tipo;
	}
	public TipoUsuario() {
		super();
	}
	public TipoUsuario(Integer id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "TipoUsuario [id=" + id + ", tipo=" + tipo + "]";
	}
	
	
}