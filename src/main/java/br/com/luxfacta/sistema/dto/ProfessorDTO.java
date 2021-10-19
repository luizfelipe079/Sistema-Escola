package br.com.luxfacta.sistema.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.luxfacta.sistema.domain.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	@JsonIgnore
	private String senha;
	
	public ProfessorDTO(Professor obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		senha = obj.getSenha();
	}
}
