package com.ivanmoreno.cursos.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivanmoreno.commons.services.CommonServiceImpl;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService{

	public CursoServiceImpl(CursoRepository repository) {
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return this.repository.findCursoByAlumnoId(id);
	}

}
