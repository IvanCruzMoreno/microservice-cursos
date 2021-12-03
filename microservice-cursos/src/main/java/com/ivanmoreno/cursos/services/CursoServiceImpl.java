package com.ivanmoreno.cursos.services;


import org.springframework.stereotype.Service;

import com.ivanmoreno.commons.services.CommonServiceImpl;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService{

	public CursoServiceImpl(CursoRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
	}

}
