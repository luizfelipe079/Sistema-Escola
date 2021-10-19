package br.com.luxfacta.sistema.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;

	@Column(name = "dataNasc")
	private Date dataNasc;
	
	@OneToMany(mappedBy = "aluno")
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@OneToMany(mappedBy = "aluno")
	private List<Nota> notas = new ArrayList<>();
	
	public Aluno(Integer id, String nome, String email, Date dataNasc) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNasc = dataNasc;
	}
}
