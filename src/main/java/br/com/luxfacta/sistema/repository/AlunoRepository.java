package br.com.luxfacta.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luxfacta.sistema.domain.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

}
