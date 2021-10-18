package br.com.luxfacta.sistema.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "notas")
public class Nota {
	
	@ManyToOne
	@JoinColumn(name = "disciplina_id")
	private Disciplina disciplina;

	@Column(name = "nota1")
	private Double nota1;
	
	@Column(name = "nota2")
	private Double nota2;
}
