package com.ivanmoreno.cursos.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ivanmoreno.commons.controllers.CommonController;
import com.ivanmoreno.commons.models.entity.Alumno;
import com.ivanmoreno.commons.models.entity.Examen;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		cursoDB.setNombre(curso.getNombre());
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
	
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> addAlumnosToCurso(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		alumnos.forEach(alumno -> cursoDB.addAlumno(alumno));
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
	
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> removeAlumnosToCurso(@RequestBody Alumno alumno, @PathVariable Long id) {
		
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		
		cursoDB.removeAlumno(alumno);
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> findByAlumnoId(@PathVariable Long id) {
		Curso curso = this.service.findCursoByAlumnoId(id);
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> addExamenesToCurso(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		examenes.forEach(cursoDB::addExamen);
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> removeExamenesToCurso(@RequestBody Examen examen, @PathVariable Long id) {
		
		Optional<Curso> cursoOpt = this.service.findById(id);
		
		if(!cursoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDB = cursoOpt.get();
		
		cursoDB.removeExamen(examen);
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
}
