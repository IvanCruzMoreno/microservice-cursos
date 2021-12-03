package com.ivanmoreno.cursos.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ivanmoreno.commons.models.entity.Alumno;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "cursos")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id", referencedColumnName = "id")
	private List<Alumno> alumnos;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public Curso() {
		this.alumnos = new ArrayList<>();
	}
	
	public void addAlumno(Alumno alumno) {
		this.alumnos.add(alumno);
	}
	
	public void removeAlumno(Alumno alumno) {
		this.alumnos.remove(alumno);
	}
}
