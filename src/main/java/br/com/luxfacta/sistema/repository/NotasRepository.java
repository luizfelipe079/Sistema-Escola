package br.com.luxfacta.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luxfacta.sistema.domain.Nota;

public interface NotasRepository extends JpaRepository<Nota, Integer>{

}
