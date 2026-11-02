package com.example.clyvo_challenge3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "T_CLYVOJ_CLINICA_VET")
public class ClinicaVeterinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_clinica_vet")
	private Long id;

	@NotNull(message = "O veterinário é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_veterinario", nullable = false)
	private Veterinario veterinario;

	@NotNull(message = "A clínica é obrigatória")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_clinica", nullable = false)
	private Clinica clinica;

    public ClinicaVeterinario() {
        super();
    }

    public ClinicaVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public ClinicaVeterinario(Veterinario veterinario, Clinica clinica) {
        this.veterinario = veterinario;
        this.clinica = clinica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }
}