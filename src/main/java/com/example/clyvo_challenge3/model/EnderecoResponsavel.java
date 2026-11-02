package com.example.clyvo_challenge3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_CLYVOJ_ENDERECO_RESPONSAVEL")
public class EnderecoResponsavel extends EnderecoBase {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_responsavel",nullable = false,unique = true)
	private Responsavel responsavel;
	
	

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}
	
	public void transferirEnderecoResponsavel(EnderecoResponsavel endereco_form) {
		super.setPais(endereco_form.getPais());
		super.setEstado(endereco_form.getEstado());
		super.setCidade(endereco_form.getCidade());
		super.setBairro(endereco_form.getBairro());
		super.setLogradouro(endereco_form.getLogradouro());
		super.setNumeroRua(endereco_form.getNumeroRua());
		super.setCep(endereco_form.getCep());
	}
	
	
	
	
}
