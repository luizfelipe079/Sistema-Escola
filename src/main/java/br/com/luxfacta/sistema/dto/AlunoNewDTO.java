package br.com.luxfacta.sistema.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AlunoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private Date dataNasc;
}
