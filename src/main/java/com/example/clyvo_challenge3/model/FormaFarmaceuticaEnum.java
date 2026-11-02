package com.example.clyvo_challenge3.model;

public enum FormaFarmaceuticaEnum {
	SOLIDA("Sólida"), SEMI_SOLIDA("Semi-Sólida"), LIQUIDA("Líquida");
	
	private String descricao;

	private FormaFarmaceuticaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	
	
}
