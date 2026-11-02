package com.example.clyvo_challenge3.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Testes unitarios das regras de validacao da entidade Medicamento.
 */
@DisplayName("Validações do Medicamento")
class MedicamentoValidacaoTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	static void iniciarValidador() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	static void fecharValidador() {
		factory.close();
	}

	private Medicamento criarMedicamentoValido() {
		return new Medicamento(null, "Amoxicilina", 20, 10, new BigDecimal("250.00"), "mg", 12, "horas",
				FormaFarmaceuticaEnum.SOLIDA, new Prescricao());
	}

	@Test
	@DisplayName("Medicamento com todos os dados corretos é válido")
	void medicamentoValidoNaoTemErros() {
		assertTrue(validator.validate(criarMedicamentoValido()).isEmpty());
	}

	@Test
	@DisplayName("Dose precisa ser maior que zero")
	void doseZeroEhInvalida() {
		Medicamento medicamento = criarMedicamentoValido();
		medicamento.setDose(BigDecimal.ZERO);

		assertFalse(validator.validateProperty(medicamento, "dose").isEmpty());
	}

	@Test
	@DisplayName("Quantidade de dias precisa ser pelo menos 1")
	void quantidadeDiasZeroEhInvalida() {
		Medicamento medicamento = criarMedicamentoValido();
		medicamento.setQuantidadeDias(0);

		assertFalse(validator.validateProperty(medicamento, "quantidadeDias").isEmpty());
	}

	@Test
	@DisplayName("Forma farmacêutica é obrigatória")
	void formaFarmaceuticaNulaEhInvalida() {
		Medicamento medicamento = criarMedicamentoValido();
		medicamento.setFormaFarmaceutica(null);

		assertFalse(validator.validateProperty(medicamento, "formaFarmaceutica").isEmpty());
	}
}
