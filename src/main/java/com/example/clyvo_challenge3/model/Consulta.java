package com.example.clyvo_challenge3.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_CONSULTA")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_consulta")
	private Long id;

	@NotBlank(message = "O histórico da consulta é obrigatório")
	@Size(max = 200, message = "O histórico deve ter no máximo 200 caracteres")
	@Column(name = "historico_consulta", nullable = false, length = 200)
	private String historico;

	@NotNull(message = "O status da consulta é obrigatório")
	@Column(name = "st_consulta", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private StatusConsultaEnum statusConsulta;

	@NotNull(message = "A data da consulta é obrigatória")
	@Column(name = "dt_consulta", nullable = false)
	private LocalDate dataConsulta;

	// Relacionamento com Veterinário (Muitas consultas para Um Veterinário)
	@NotNull(message = "O veterinário/clínica é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vet_cli", nullable = false, unique = false)
	private ClinicaVeterinario veterinarioClinica;

	// Relacionamento com Animal (Muitas consultas para Um Animal)
	@NotNull(message = "O animal é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_animal", nullable = false, unique = false)
	private Animal animal;

	@OneToOne(mappedBy = "consulta", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Prescricao prescricao;

	public Consulta() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public StatusConsultaEnum getStatusConsulta() {
		return statusConsulta;
	}

	public void setStatusConsulta(StatusConsultaEnum statusConsulta) {
		this.statusConsulta = statusConsulta;
	}

	public ClinicaVeterinario getVeterinarioClinica() {
		return veterinarioClinica;
	}

	public void setVeterinarioClinica(ClinicaVeterinario veterinarioClinica) {
		this.veterinarioClinica = veterinarioClinica;
	}

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
}