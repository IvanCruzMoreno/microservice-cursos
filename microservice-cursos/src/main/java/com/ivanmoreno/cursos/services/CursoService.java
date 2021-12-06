package com.ivanmoreno.cursos.services;

import com.ivanmoreno.commons.services.CommonService;
import com.ivanmoreno.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso>{
	
	public Curso findCursoByAlumnoId(Long id);
}
