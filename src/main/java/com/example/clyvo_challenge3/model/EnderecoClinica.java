package com.example.clyvo_challenge3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_CLYVOJ_ENDERECO_CLINICA")
public class EnderecoClinica extends EnderecoBase{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_clinica",nullable = false,unique = false)
	private Clinica clinica;

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}
	
	

}
