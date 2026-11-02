package com.example.clyvo_challenge3.model;

public enum TipoUsuarioEnum {
	
	CLINICA("Clínica"),VETERINARIO("Veterinário");
	
	private String descricao;

	private TipoUsuarioEnum(String descricao) {
		this.descricao = descricao;
	}
	
	

}
