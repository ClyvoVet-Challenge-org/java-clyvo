package com.example.clyvo_challenge3.dto.request;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VeterinarioEdicaoRequest(

		@NotBlank(message = "O nome é obrigatório")
		@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
		String nome,

		@NotBlank(message = "O CPF é obrigatório")
		@CPF(message = "CPF inválido")
		String cpf,

		@NotBlank(message = "O CRMV é obrigatório")
		@Pattern(regexp = "^[A-Z]{2}\\d{3,6}$", message = "CRMV deve estar no formato UFNNNNN (ex: SP1234)")
		@Size(max = 20, message = "O CRMV deve ter no máximo 20 caracteres")
		String crmv

) {}