package com.dan.usuario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="USR_TIPO_OBRA", schema="MS_USR", 
		uniqueConstraints =	{@UniqueConstraint(columnNames= {"DESCRIPCION"})})
public class TipoObra {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descripcion;
	
	public TipoObra(int i) {
		this.id = i;
	}
	
	public TipoObra() {
		
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "TipoObra [id=" + id + ", descripcion=" + descripcion + "]";
	}
	
}