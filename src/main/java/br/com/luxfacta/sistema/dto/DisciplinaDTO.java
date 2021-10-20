package br.com.luxfacta.sistema.dto;

import java.io.Serializable;

import br.com.luxfacta.sistema.domain.Disciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisciplinaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public DisciplinaDTO(Disciplina obj) {
		id = obj.getId();
		nome = obj.getNome();
	}
}
