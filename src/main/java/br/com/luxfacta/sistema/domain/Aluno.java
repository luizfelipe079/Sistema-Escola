package br.com.luxfacta.sistema.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "aluno")
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matricula")
	private Integer matricula;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "dataNasc")
	private String dataNasc;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(mappedBy = "aluno")
	private List<Disciplina> disciplinas = new ArrayList<>();
}
