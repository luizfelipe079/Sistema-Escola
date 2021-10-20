package br.com.luxfacta.sistema.domain.enums;

public enum Perfil {
	PROF(1, "ROLE_PROF"),
	ALUNO(2, "ROLE_ALUNO");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if( cod == null ) {
			return null;
		}
		
		for(Perfil x : Perfil .values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id Inválido : " + cod);
	}
}
