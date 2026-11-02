package com.example.clyvo_challenge3.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class AnimalEdicaoDTO {

	@NotBlank(message = "O nome do animal é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatória")
	@Past(message = "A data de nascimento deve ser uma data passada")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;

	@NotNull(message = "O peso é obrigatório")
	@DecimalMin(value = "0.01", message = "O peso deve ser maior que zero")
	@Digits(integer = 3, fraction = 2, message = "Peso inválido (máx. 3 dígitos inteiros e 2 decimais)")
	private BigDecimal peso;

	@NotBlank(message = "A espécie é obrigatória")
	@Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
	private String especie;

	@NotBlank(message = "A raça é obrigatória")
	@Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
	private String raca;

	@Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
	private String rg;

	@Size(max = 20, message = "O número do microchip deve ter no máximo 20 caracteres")
	private String numeroMicrochip;

	@Valid
	private EnderecoAnimalEdicaoDTO enderecoAnimal;

	public EnderecoAnimalEdicaoDTO getEnderecoAnimal() {
		return enderecoAnimal;
	}

	public void setEnderecoAnimal(EnderecoAnimalEdicaoDTO enderecoAnimal) {
		this.enderecoAnimal = enderecoAnimal;
	}

	public AnimalEdicaoDTO() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNumeroMicrochip() {
		return numeroMicrochip;
	}

	public void setNumeroMicrochip(String numeroMicrochip) {
		this.numeroMicrochip = numeroMicrochip;
	}

}
