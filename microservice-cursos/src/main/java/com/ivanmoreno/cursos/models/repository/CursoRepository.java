package com.ivanmoreno.cursos.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.ivanmoreno.cursos.models.entity.Curso;

public interface CursoRepository extends CrudRepository<Curso, Long>{

}
