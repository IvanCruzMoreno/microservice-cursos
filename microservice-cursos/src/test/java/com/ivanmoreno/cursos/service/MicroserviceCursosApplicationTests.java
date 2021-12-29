package com.ivanmoreno.cursos.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ivanmoreno.commons.models.entity.Alumno;
import com.ivanmoreno.cursos.DatosTest;
import com.ivanmoreno.cursos.clients.AlumnoClientFeign;
import com.ivanmoreno.cursos.clients.RespuestaClientFeign;
import com.ivanmoreno.cursos.models.entity.Curso;
import com.ivanmoreno.cursos.models.repository.CursoRepository;
import com.ivanmoreno.cursos.services.CursoService;
import com.ivanmoreno.cursos.services.CursoServiceImpl;

@SpringBootTest
class MicroserviceCursosApplicationTests {

	private RespuestaClientFeign respuestaClient;
	private AlumnoClientFeign alumnoClient;
	private CursoRepository cursoRepository;
	
	private CursoService cursoService;
	
	@BeforeEach
	void setUp() {
		respuestaClient = mock(RespuestaClientFeign.class);
		alumnoClient = mock(AlumnoClientFeign.class);
		cursoRepository = mock(CursoRepository.class);
		
		cursoService = new CursoServiceImpl(cursoRepository, respuestaClient, alumnoClient);
	}
	
	@Test
	@DisplayName("Find Curso by Alumno Id")
	void findCursoByAlumnoIdTest() {
		Long value = 1L;
		
		Curso expectedCurso = DatosTest.createCurso001().orElse(null);
		when(cursoRepository.findCursoByAlumnoId(value)).thenReturn(expectedCurso);
		
		Curso result = cursoService.findCursoByAlumnoId(value);
		
		assertSame(expectedCurso, result);
	}
	
	@Test
	@DisplayName("Get Examenes Id by Alumno Id")
	void obtenerExamenesIdsByAlumnoIdTest() {
		Long value = 1L;
		
		List<Long> expectedIds = Arrays.asList(1L, 3L);
		when(respuestaClient.obtenerExamenesIdsByAlumnoId(value)).thenReturn(expectedIds);
		
		List<Long> result = cursoService.obtenerExamenesIdsByAlumnoId(value);
		
		assertSame(expectedIds, result);
	}
	
	@Test
	@DisplayName("Get Alumnos by Alumnos Id")
	void obtenerAlumnosPorIdsTest() {
		List<Long> values = Arrays.asList(1L, 3L);
		
		List<Alumno> expectedAlumnos = DatosTest.createAlumnos();
		when(alumnoClient.obtenerAlumnosPorIds(values)).thenReturn(expectedAlumnos);
		
		List<Alumno> result = cursoService.obtenerAlumnosPorIds(values);
		
		assertSame(expectedAlumnos, result);
	}
	
	@Test
	@DisplayName("Delete Curso Alumno by Alumno Id")
	void deleteCursoAlumnoByIdTest() {
		Long value = 1L;
		
		cursoService.deleteCursoAlumnoById(value);
		
		verify(cursoRepository, times(1)).deleteCursoAlumnoById(value);
	}
}
