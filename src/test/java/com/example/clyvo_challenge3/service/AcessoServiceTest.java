package com.example.clyvo_challenge3.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.clyvo_challenge3.model.Animal;
import com.example.clyvo_challenge3.model.Responsavel;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.model.Veterinario;
import com.example.clyvo_challenge3.repository.AnimalRepository;
import com.example.clyvo_challenge3.repository.ConsultaRepository;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

/**
 * Testes unitarios da regra de acesso: um veterinario so pode ver
 * responsaveis/animais que ele ja atendeu.
 * Os repositorios sao simulados com Mockito, entao nao acessam o banco.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Regras de acesso do Veterinário")
class AcessoServiceTest {

	@Mock
	private UsuarioRepository repUsuario;

	@Mock
	private ConsultaRepository repConsulta;

	@Mock
	private AnimalRepository repAnimal;

	@InjectMocks
	private AcessoService acessoService;

	private Authentication usuarioLogado(String username) {
		Authentication auth = mock(Authentication.class);
		when(auth.getName()).thenReturn(username);
		return auth;
	}

	private Usuario usuarioVeterinario(Long idVeterinario) {
		Veterinario vet = new Veterinario();
		vet.setId(idVeterinario);

		Usuario usuario = new Usuario();
		usuario.setUsername("camila@email.com");
		usuario.setVeterinario(vet);
		return usuario;
	}

	@Test
	@DisplayName("Veterinário que atendeu o responsável tem acesso")
	void veterinarioQueAtendeuTemAcesso() {
		Authentication auth = usuarioLogado("camila@email.com");
		when(repUsuario.findByUsername("camila@email.com")).thenReturn(Optional.of(usuarioVeterinario(1L)));
		when(repConsulta.veterinarioAtendeResponsavel(1L, 10L)).thenReturn(true);

		assertTrue(acessoService.veterinarioAcessaResponsavel(auth, 10L));
	}

	@Test
	@DisplayName("Veterinário que nunca atendeu o responsável não tem acesso")
	void veterinarioQueNaoAtendeuNaoTemAcesso() {
		Authentication auth = usuarioLogado("camila@email.com");
		when(repUsuario.findByUsername("camila@email.com")).thenReturn(Optional.of(usuarioVeterinario(1L)));
		when(repConsulta.veterinarioAtendeResponsavel(1L, 99L)).thenReturn(false);

		assertFalse(acessoService.veterinarioAcessaResponsavel(auth, 99L));
	}

	@Test
	@DisplayName("Usuário inexistente não tem acesso")
	void usuarioInexistenteNaoTemAcesso() {
		Authentication auth = usuarioLogado("ninguem@email.com");
		when(repUsuario.findByUsername("ninguem@email.com")).thenReturn(Optional.empty());

		assertFalse(acessoService.veterinarioAcessaResponsavel(auth, 10L));
	}

	@Test
	@DisplayName("Usuário de clínica (sem veterinário) não tem acesso de veterinário")
	void usuarioClinicaNaoTemAcesso() {
		Authentication auth = usuarioLogado("contato@amigofiel.com");
		Usuario clinica = new Usuario();
		clinica.setUsername("contato@amigofiel.com");
		when(repUsuario.findByUsername("contato@amigofiel.com")).thenReturn(Optional.of(clinica));

		assertFalse(acessoService.veterinarioAcessaResponsavel(auth, 10L));
	}

	@Test
	@DisplayName("Animal inexistente não pode ser acessado")
	void animalInexistenteNaoTemAcesso() {
		Authentication auth = mock(Authentication.class);
		when(repAnimal.findById(500L)).thenReturn(Optional.empty());

		assertFalse(acessoService.veterinarioAcessaAnimal(auth, 500L));
	}

	@Test
	@DisplayName("Veterinário acessa o animal se atendeu o responsável dele")
	void veterinarioAcessaAnimalDoResponsavelAtendido() {
		Authentication auth = usuarioLogado("camila@email.com");

		Responsavel responsavel = new Responsavel();
		responsavel.setId(10L);
		Animal animal = new Animal();
		animal.setResponsavel(responsavel);

		when(repAnimal.findById(1L)).thenReturn(Optional.of(animal));
		when(repUsuario.findByUsername("camila@email.com")).thenReturn(Optional.of(usuarioVeterinario(1L)));
		when(repConsulta.veterinarioAtendeResponsavel(1L, 10L)).thenReturn(true);

		assertTrue(acessoService.veterinarioAcessaAnimal(auth, 1L));
	}
}
