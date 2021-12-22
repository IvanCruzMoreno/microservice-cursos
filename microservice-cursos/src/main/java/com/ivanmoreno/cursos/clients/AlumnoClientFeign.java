package com.ivanmoreno.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivanmoreno.commons.models.entity.Alumno;

@FeignClient(name = "microservice-alumnos")
public interface AlumnoClientFeign {

	@GetMapping("/alumnos-por-ids")
	public List<Alumno> obtenerAlumnosPorIds(@RequestParam List<Long> ids);
}
