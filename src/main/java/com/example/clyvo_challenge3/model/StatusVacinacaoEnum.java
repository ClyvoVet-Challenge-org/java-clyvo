package com.example.clyvo_challenge3.model;

public enum StatusVacinacaoEnum {
	
	EFETUADA("Efetuada"), PENDENTE("Pendente"), ATRASADA("Atrasada"), CANCELADA("Cancelada");
	
	
	private String descricao;

	private StatusVacinacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
		

}
