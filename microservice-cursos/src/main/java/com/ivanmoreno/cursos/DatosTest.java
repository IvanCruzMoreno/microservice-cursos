package com.ivanmoreno.cursos;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ivanmoreno.commons.models.entity.Alumno;
import com.ivanmoreno.commons.models.entity.Examen;
import com.ivanmoreno.cursos.models.entity.Curso;

public class DatosTest {

	public static Optional<Curso> createCurso001() {
		Curso curso = new Curso();
		curso.setId(1L);
		curso.setNombre("Curso 1");
		curso.setAlumnos(createAlumnos());
		curso.setExamenes(createExamen());
		return Optional.of(curso);
	}
	
	public static List<Alumno> createAlumnos() {
		return Arrays.asList(
				new Alumno(1L, "Usuario 1", "UNO", "u1@gmail.com", null, null), 
				new Alumno(2L, "Usuario 2", "DOS", "u2@gmail.com", null, null),
				new Alumno(3L, "Usuario 3", "TRES", "u3@gmail.com", null, null));
	}
	
	private static List<Examen> createExamen() {
		return Arrays.asList(
				new Examen(1L, "Examen 1", null, null, null, false),
				new Examen(2L, "Examen 2", null, null, null, false),
				new Examen(3L, "Examen 3", null, null, null, false));
	}
}
