package br.com.luxfacta.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luxfacta.sistema.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}
