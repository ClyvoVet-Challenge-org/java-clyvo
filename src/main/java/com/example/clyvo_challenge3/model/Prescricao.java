package com.example.clyvo_challenge3.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_PRESCRICAO")
public class Prescricao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prescricao")
	private Long id;
	
	@NotNull(message = "A data de emissão é obrigatória")
	@PastOrPresent(message = "A data de emissão deve ser hoje")
	@Column(name = "dt_emissao", nullable = false)
	private LocalDate dataEmissao;

	@NotNull(message = "A data de expiração é obrigatória")
	@Future(message = "A data de expiração deve ser uma data futura")
	@Column(name = "dt_expiracao", nullable = false)
	private LocalDate dataExpiracao;

	@Size(max = 255, message = "As observações devem ter no máximo 255 caracteres")
	@Column(name = "observacoes_gerais",length = 255)
	private String observacoesGerais;
	
	@NotNull(message = "A consulta é obrigatória")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta", nullable = false, unique = true )
	private Consulta consulta;

	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "prescricao",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Medicamento> medicamentos =new ArrayList<Medicamento>();
}
