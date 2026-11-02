package com.example.clyvo_challenge3.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Testes unitarios das regras de validacao da entidade Animal.
 * Nao acessam o banco: validam apenas as anotacoes (@NotBlank, @Past, @DecimalMin...).
 */
@DisplayName("Validações do Animal")
class AnimalValidacaoTest {

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

	private Animal criarAnimalValido() {
		Animal animal = new Animal();
		animal.setNome("Nina");
		animal.setDataNascimento(LocalDate.of(2021, 2, 10));
		animal.setPeso(new BigDecimal("9.40"));
		animal.setEspecie("Canina");
		animal.setRaca("Poodle");
		animal.setResponsavel(new Responsavel());
		return animal;
	}

	@Test
	@DisplayName("Animal com todos os dados corretos é válido")
	void animalValidoNaoTemErros() {
		Set<ConstraintViolation<Animal>> erros = validator.validate(criarAnimalValido());
		assertTrue(erros.isEmpty(), "Não deveria haver erros de validação: " + erros);
	}

	@Test
	@DisplayName("Nome do animal é obrigatório")
	void nomeEmBrancoEhInvalido() {
		Animal animal = criarAnimalValido();
		animal.setNome("   ");

		Set<ConstraintViolation<Animal>> erros = validator.validateProperty(animal, "nome");

		assertEquals(1, erros.size());
		assertEquals("O nome do animal é obrigatório", erros.iterator().next().getMessage());
	}

	@Test
	@DisplayName("Data de nascimento não pode ser no futuro")
	void dataNascimentoFuturaEhInvalida() {
		Animal animal = criarAnimalValido();
		animal.setDataNascimento(LocalDate.now().plusDays(1));

		assertFalse(validator.validateProperty(animal, "dataNascimento").isEmpty());
	}

	@Test
	@DisplayName("Peso precisa ser maior que zero")
	void pesoZeroEhInvalido() {
		Animal animal = criarAnimalValido();
		animal.setPeso(BigDecimal.ZERO);

		assertFalse(validator.validateProperty(animal, "peso").isEmpty());
	}

	@Test
	@DisplayName("Peso aceita no máximo 3 dígitos inteiros e 2 decimais")
	void pesoComMuitosDigitosEhInvalido() {
		Animal animal = criarAnimalValido();
		animal.setPeso(new BigDecimal("1234.567"));

		assertFalse(validator.validateProperty(animal, "peso").isEmpty());
	}

	@Test
	@DisplayName("Animal precisa ter um responsável")
	void animalSemResponsavelEhInvalido() {
		Animal animal = criarAnimalValido();
		animal.setResponsavel(null);

		assertFalse(validator.validateProperty(animal, "responsavel").isEmpty());
	}
}
