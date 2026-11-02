package com.example.clyvo_challenge3.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_ANIMAL")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_animal")
	private Long id;

	@NotBlank(message = "O nome do animal é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Column(name = "nm_animal", nullable = false, length = 100) // Obrigatório
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatória")
	@Past(message = "A data de nascimento deve ser uma data passada")
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "dt_nascimento_animal", nullable = false) // Obrigatório
	private LocalDate dataNascimento;

	@NotNull(message = "O peso é obrigatório")
	@DecimalMin(value = "0.01", message = "O peso deve ser maior que zero")
	@Digits(integer = 3, fraction = 2, message = "Peso inválido (máx. 3 dígitos inteiros e 2 decimais)")
	@Column(name = "peso_animal", nullable = false, precision = 5, scale = 2) // Obrigatório
	private BigDecimal peso;

	@NotBlank(message = "A espécie é obrigatória")
	@Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
	@Column(name = "especie_animal", nullable = false, length = 50) // Obrigatório
	private String especie;

	@NotBlank(message = "A raça é obrigatória")
	@Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
	@Column(name = "raca_animal", nullable = false, length = 50) // Obrigatório
	private String raca;

	@Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
	@Column(name = "rg_animal", length = 20, unique = true, nullable = true) // Opcional para adicionar depois
	private String rg; 

	@Size(max = 20, message = "O número do microchip deve ter no máximo 20 caracteres")
	@Column(name = "nr_microchip_animal", length = 20, unique = true, nullable = true) // Opcional para adicionar depois
	private String numeroMicrochip;

	@NotNull(message = "O responsável é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_responsavel", nullable = false,unique = false)
	private Responsavel responsavel;
	
	@OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
	private List<Consulta> consultas  = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "animal",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private List<Vacinacao> vacinacoes = new ArrayList<>();
	
	@Valid
	@OneToOne(mappedBy = "animal",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private EnderecoAnimal enderecoAnimal;

	public Animal() {
		super();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Vacinacao> getVacinacoes() {
		return vacinacoes;
	}

	public void setVacinacoes(List<Vacinacao> vacinacoes) {
		this.vacinacoes = vacinacoes;
	}


	public List<Consulta> getConsultas() {
		return consultas;
	}


	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}


	public EnderecoAnimal getEnderecoAnimal() {
		return enderecoAnimal;
	}


	public void setEnderecoAnimal(EnderecoAnimal enderecoAnimal) {
		this.enderecoAnimal = enderecoAnimal;
	}
	
	

}