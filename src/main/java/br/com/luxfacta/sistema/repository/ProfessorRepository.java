package br.com.luxfacta.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.luxfacta.sistema.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
	
	@Transactional(readOnly = true)
	Professor findByEmail(String email);
	
}
