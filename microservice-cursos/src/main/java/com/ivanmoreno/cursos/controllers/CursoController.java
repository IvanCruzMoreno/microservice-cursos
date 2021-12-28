package com.ivanmoreno.cursos.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ivanmoreno.commons.controllers.CommonController;
import com.ivanmoreno.commons.models.entity.Alumno;
import com.ivanmoreno.commons.models.entity.Examen;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.models.entity.CursoAlumno;
import com.ivanmoreno.cursos.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@GetMapping
	@Override
	public ResponseEntity<?> showAll() {
		List<Curso> entities = ((List<Curso>) service.findAll())
				.stream()
				.map(this::addAlumnoToCursoByCursoAlumnos)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(entities);
	}
	
	private Curso addAlumnoToCursoByCursoAlumnos(Curso curso) {
		
		curso.getCursoAlumnos().forEach(
				cursoAlumno -> {
					Alumno alumno = new Alumno();
					alumno.setId(cursoAlumno.getAlumnoId());
					curso.addAlumno(alumno);
					});
		
		return curso;
	}
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> showAlumno(@PathVariable Long id) {
		Optional<Curso> entity = service.findById(id);
		
		if(!entity.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso curso = entity.get();
		if(!curso.getCursoAlumnos().isEmpty()) {
			
			List<Long> idsAlumnos = curso.getCursoAlumnos()
					.stream()
					.map(CursoAlumno::getAlumnoId)
					.collect(Collectors.toList());
			
			List<Alumno> alumnos = service.obtenerAlumnosPorIds(idsAlumnos);
			curso.setAlumnos(alumnos);
		}
		
		return ResponseEntity.ok().body(curso);
	}
	
	@GetMapping("/page")
	@Override
	public ResponseEntity<?> showAll(Pageable pageable) {
		Page<Curso> cursos = service.findAll(pageable)
				.map(this::addAlumnoToCursoByCursoAlumnos);
		
		return ResponseEntity.ok().body(cursos);
	}
	
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
		alumnos.forEach(alumno -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alumno.getId());
			cursoAlumno.setCurso(cursoDB);
			
			cursoDB.addCursoAlumno(cursoAlumno);
		});
		
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
		
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		cursoAlumno.setCurso(null);
		
		cursoDB.removeCursoAlumno(cursoAlumno);
		
		Curso cursoSaved = this.service.save(cursoDB);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoSaved);
	}
	
	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> removeCursoAlumnoById(@PathVariable Long id) {
		service.deleteCursoAlumnoById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> findByAlumnoId(@PathVariable Long id) {
		Curso curso = this.service.findCursoByAlumnoId(id);
		
		if(curso != null) {
			
			List<Long> examenesIds = this.service.obtenerExamenesIdsByAlumnoId(id);
			
			if(examenesIds != null && examenesIds.size() > 0) {
				
				List<Examen> examenes = curso.getExamenes()
						.stream()
						.map(examen -> {
							if(examenesIds.contains(examen.getId())) {
								examen.setRespondido(true);
							}
							return examen;
						})
						.collect(Collectors.toList());
				
				curso.setExamenes(examenes);
			}
			
		}
		
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
