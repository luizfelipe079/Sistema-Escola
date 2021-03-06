package br.com.luxfacta.sistema.dto;

import java.io.Serializable;

import br.com.luxfacta.sistema.domain.Aluno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	
	public AlunoDTO(Aluno obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
}
