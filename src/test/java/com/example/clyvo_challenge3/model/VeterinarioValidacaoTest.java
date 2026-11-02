package com.example.clyvo_challenge3.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Testes unitarios das regras de validacao do Veterinario (CRMV e email).
 */
@DisplayName("Validações do Veterinário")
class VeterinarioValidacaoTest {

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

	private Veterinario criarVeterinario(String crmv, String email) {
		return new Veterinario(null, "Dra. Camila Torres", "529.982.247-25", crmv, email, "senhaForte1");
	}

	@Test
	@DisplayName("CRMV no formato UF + números é válido")
	void crmvNoFormatoCorretoEhValido() {
		Veterinario vet = criarVeterinario("SP1234", "camila@email.com");

		assertTrue(validator.validateProperty(vet, "crmv").isEmpty());
	}

	@Test
	@DisplayName("CRMV fora do formato é inválido")
	void crmvForaDoFormatoEhInvalido() {
		Veterinario vet = criarVeterinario("1234SP", "camila@email.com");

		assertFalse(validator.validateProperty(vet, "crmv").isEmpty());
	}

	@Test
	@DisplayName("Email precisa ter formato válido")
	void emailInvalidoEhRejeitado() {
		Veterinario vet = criarVeterinario("SP1234", "email-sem-arroba");

		assertFalse(validator.validateProperty(vet, "email").isEmpty());
	}
}
