package com.dan.usuario.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dan.usuario.aspect.LogAspect;

@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("execution(* com.dan.usuario.service.ClienteService.*(..))")
	private void ClienteServiceMetodos() {};
	
	@Pointcut("execution(* com.dan.usuario.service.ObraService.*(..))")
	private void ObraServiceMetodos() {};
	
	@Pointcut("execution(* com.dan.usuario.service.EmpleadoService.*(..))")
	private void EmpleadoServiceMetodos() {};
	
	@Pointcut("execution(* com.dan.usuario.service.UsuarioService.*(..))")
	private void UsuarioServiceMetodos() {};
	
	@Before("ClienteServiceMetodos() || ObraServiceMetodos() || EmpleadoServiceMetodos() || UsuarioServiceMetodos()")
	public void hacerAntes(JoinPoint jp) {
		System.out.println("\n");
		
		//TRACE -> DEBUG -> INFO -> WARN -> ERROR -> FATAL
		logger.info("A continuacion se ejecutara el metodo: " + jp.getSignature().getName());
		logger.info("Metodo de la clase: " + jp.getTarget().getClass());
		logger.info("Argumentos: " + Arrays.toString(jp.getArgs()));
	}
	
	@After("ClienteServiceMetodos() || ObraServiceMetodos() || EmpleadoServiceMetodos() || UsuarioServiceMetodos()")
	public void hacerDespues(JoinPoint jp) {
		logger.info("Se ha ejecutado el metodo: "+ jp.getSignature().getName());
	}
}
