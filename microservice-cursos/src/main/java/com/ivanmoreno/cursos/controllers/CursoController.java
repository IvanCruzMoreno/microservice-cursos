package com.ivanmoreno.cursos.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ivanmoreno.commons.controllers.CommonController;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		cursoDB.setNombre(curso.getNombre());
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
}
