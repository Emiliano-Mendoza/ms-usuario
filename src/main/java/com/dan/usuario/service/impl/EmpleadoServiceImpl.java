package com.dan.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.usuario.dao.EmpleadoRepository;
import com.dan.usuario.domain.Empleado;
import com.dan.usuario.service.EmpleadoService;
@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepo;
	
	@Override
	public Empleado createEmpleado(Empleado emp) {
		return empleadoRepo.save(emp);
	}

	@Override
	public List<Empleado> getAllEmpleados() {
		return empleadoRepo.findAll();
	}

	@Override
	public void deleteEmpleado(Empleado emp) {
		empleadoRepo.delete(emp);
	}

	@Override
	public void deleteEmpleadoPorId(Integer id) {
		empleadoRepo.deleteById(id);
	}

	@Override
	public Optional<Empleado> findById(Integer id) {
		return empleadoRepo.findById(id);
	}

}
