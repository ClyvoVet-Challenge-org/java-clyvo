package com.example.clyvo_challenge3.model;

public enum StatusContratoEnum {

	ATIVO("Ativo"), INATIVO("Inativo");

	private String descricao;

	private StatusContratoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}