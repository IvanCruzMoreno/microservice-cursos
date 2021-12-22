package com.ivanmoreno.cursos.models.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"curso", "id"})
@Entity
@Table(name = "curso_alumnos")
public class CursoAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "alumno_id", unique = true)
	private Long alumnoId;
	
	@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "cursoAlumnos"}, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id", referencedColumnName = "id")
	private Curso curso;
}
