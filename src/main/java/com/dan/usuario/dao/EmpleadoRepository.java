package com.dan.usuario.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.usuario.domain.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

}
