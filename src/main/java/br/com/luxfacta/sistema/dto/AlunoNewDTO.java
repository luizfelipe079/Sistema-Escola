package br.com.luxfacta.sistema.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AlunoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private String senha;
}
