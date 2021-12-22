package com.ivanmoreno.cursos.services;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivanmoreno.commons.models.entity.Alumno;
import com.ivanmoreno.commons.services.CommonServiceImpl;
import com.ivanmoreno.cursos.clients.AlumnoClientFeign;
import com.ivanmoreno.cursos.clients.RespuestaClientFeign;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService{

	private RespuestaClientFeign respuestaClient;
	private AlumnoClientFeign alumnoClient;
	
	public CursoServiceImpl(CursoRepository repository, RespuestaClientFeign client, AlumnoClientFeign alumnoFeign) {
		super(repository);
		this.respuestaClient = client;
		this.alumnoClient = alumnoFeign;
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return this.repository.findCursoByAlumnoId(id);
	}

	@Override
	public List<Long> obtenerExamenesIdsByAlumnoId(Long alumnoId) {
		return respuestaClient.obtenerExamenesIdsByAlumnoId(alumnoId);
	}

	@Override
	public List<Alumno> obtenerAlumnosPorIds(List<Long> ids) {
		return alumnoClient.obtenerAlumnosPorIds(ids);
	}

}
