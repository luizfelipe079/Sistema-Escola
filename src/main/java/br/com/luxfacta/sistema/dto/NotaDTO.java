package br.com.luxfacta.sistema.dto;

import br.com.luxfacta.sistema.domain.Nota;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotaDTO {

	private Double nota1;
	private Double nota2;
	
	public NotaDTO(Nota obj) {
		nota1 = obj.getNota1();
		nota2 = obj.getNota2();
	}
}
