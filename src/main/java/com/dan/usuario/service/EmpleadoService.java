package com.dan.usuario.service;

import java.util.List;
import java.util.Optional;

import com.dan.usuario.domain.Empleado;

public interface EmpleadoService {
	
	public Empleado createEmpleado(Empleado emp);
	public List<Empleado> getAllEmpleados();
	public void deleteEmpleado(Empleado emp);
	public void deleteEmpleadoPorId(Integer id);
	public Optional<Empleado> findById(Integer id);
	
}
