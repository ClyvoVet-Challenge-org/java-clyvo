package com.example.clyvo_challenge3.model;

public enum StatusConsultaEnum {
	
	PASSADA("Passada"),EM_ANDAMENTO("Em andamento"),FUTURA("Futura"),CANCELADA("Cancelada");
	
	
	private String descricao;

	private StatusConsultaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	

}
