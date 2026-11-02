package com.example.clyvo_challenge3.model;

import java.math.BigDecimal;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_MEDICAMENTO")
public class Medicamento{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome do medicamento é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Column(name = "nm_medicamento", nullable = false)
	private String nome;

	@NotNull(message = "A quantidade do medicamento é obrigatória")
	@Min(value = 1, message = "A quantidade deve ser maior que zero")
	@Column(name = "qt_medicamento", nullable = false)
	private Integer quantidadeMedicamento;

	@NotNull(message = "A quantidade de dias é obrigatória")
	@Min(value = 1, message = "A quantidade de dias deve ser maior que zero")
	@Column(name = "qt_dias", nullable = false)
	private Integer quantidadeDias;

	@NotNull(message = "A dose é obrigatória")
	@DecimalMin(value = "0.01", message = "A dose deve ser maior que zero")
	@Digits(integer = 3, fraction = 2, message = "Dose inválida (máx. 3 dígitos inteiros e 2 decimais)")
	@Column(name = "vl_dose", nullable = false, precision = 5, scale = 2)
	private BigDecimal dose;

	@NotBlank(message = "A unidade da dose é obrigatória")
	@Size(max = 20, message = "A unidade da dose deve ter no máximo 20 caracteres")
	@Column(name = "un_dose", nullable = false)
	private String unidadeDose;

	@NotNull(message = "A frequência é obrigatória")
	@Min(value = 1, message = "A frequência deve ser maior que zero")
	@Column(name = "qt_frequencia", nullable = false)
	private Integer frequencia;

	@NotBlank(message = "A unidade de frequência é obrigatória")
	@Size(max = 20, message = "A unidade de frequência deve ter no máximo 20 caracteres")
	@Column(name = "un_frequencia", nullable = false)
	private String unidadeFrequencia;

	@NotNull(message = "A forma farmacêutica é obrigatória")
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_farmaceutica", nullable = false)
	private FormaFarmaceuticaEnum formaFarmaceutica;

	@NotNull(message = "A prescrição é obrigatória")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prescricao", nullable = false)
	private Prescricao prescricao;
	
	
	
	public Medicamento() {
		super();
	}

	public Medicamento(Long id,
			@NotBlank(message = "O nome do medicamento é obrigatório") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String nome,
			@NotNull(message = "A quantidade do medicamento é obrigatória") @Min(value = 1, message = "A quantidade deve ser maior que zero") Integer quantidadeMedicamento,
			@NotNull(message = "A quantidade de dias é obrigatória") @Min(value = 1, message = "A quantidade de dias deve ser maior que zero") Integer quantidadeDias,
			@NotNull(message = "A dose é obrigatória") @DecimalMin(value = "0.01", message = "A dose deve ser maior que zero") @Digits(integer = 3, fraction = 2, message = "Dose inválida (máx. 3 dígitos inteiros e 2 decimais)") BigDecimal dose,
			@NotBlank(message = "A unidade da dose é obrigatória") @Size(max = 20, message = "A unidade da dose deve ter no máximo 20 caracteres") String unidadeDose,
			@NotNull(message = "A frequência é obrigatória") @Min(value = 1, message = "A frequência deve ser maior que zero") Integer frequencia,
			@NotBlank(message = "A unidade de frequência é obrigatória") @Size(max = 20, message = "A unidade de frequência deve ter no máximo 20 caracteres") String unidadeFrequencia,
			@NotNull(message = "A forma farmacêutica é obrigatória") FormaFarmaceuticaEnum formaFarmaceutica,
			@NotNull(message = "A prescrição é obrigatória") Prescricao prescricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.quantidadeMedicamento = quantidadeMedicamento;
		this.quantidadeDias = quantidadeDias;
		this.dose = dose;
		this.unidadeDose = unidadeDose;
		this.frequencia = frequencia;
		this.unidadeFrequencia = unidadeFrequencia;
		this.formaFarmaceutica = formaFarmaceutica;
		this.prescricao = prescricao;
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

	public Integer getQuantidadeMedicamento() {
		return quantidadeMedicamento;
	}

	public void setQuantidadeMedicamento(Integer quantidadeMedicamento) {
		this.quantidadeMedicamento = quantidadeMedicamento;
	}

	public Integer getQuantidadeDias() {
		return quantidadeDias;
	}

	public void setQuantidadeDias(Integer quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}

	public BigDecimal getDose() {
		return dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public String getUnidadeDose() {
		return unidadeDose;
	}

	public void setUnidadeDose(String unidadeDose) {
		this.unidadeDose = unidadeDose;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public String getUnidadeFrequencia() {
		return unidadeFrequencia;
	}

	public void setUnidadeFrequencia(String unidadeFrequencia) {
		this.unidadeFrequencia = unidadeFrequencia;
	}

	public FormaFarmaceuticaEnum getFormaFarmaceutica() {
		return formaFarmaceutica;
	}

	public void setFormaFarmaceutica(FormaFarmaceuticaEnum formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	
	
	

}
