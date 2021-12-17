package com.ivanmoreno.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-respuestas")
public interface RespuestaClientFeign {

	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public List<Long> obtenerExamenesIdsByAlumnoId(@PathVariable Long alumnoId);
}
