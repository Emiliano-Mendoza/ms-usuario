package com.dan.usuario.domain;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USR_USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userr;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_USUARIO")
	private TipoUsuario tipoUsuario;
	
	public Usuario(Integer id, String userr, String password, TipoUsuario tipoUsuario) {
		super();
		this.id = id;
		this.userr = userr;
		this.password = password;
		this.tipoUsuario = tipoUsuario;
	}


	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return userr;
	}
	public void setUser(String userr) {
		this.userr = userr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", userr=" + userr + ", password=" + password + ", tipoUsuario=" + tipoUsuario
				+ "]";
	}
	

}
