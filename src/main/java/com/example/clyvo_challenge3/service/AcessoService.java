package com.example.clyvo_challenge3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.clyvo_challenge3.model.Animal;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.model.Veterinario;
import com.example.clyvo_challenge3.repository.AnimalRepository;
import com.example.clyvo_challenge3.repository.ConsultaRepository;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

@Service
public class AcessoService {

	@Autowired
	private UsuarioRepository repUsuario;

	@Autowired
	private ConsultaRepository repConsulta;

	@Autowired
	private AnimalRepository repAnimal;

	public boolean veterinarioAcessaResponsavel(Authentication authentication, Long idResponsavel) {
		Optional<Usuario> usuario = repUsuario.findByUsername(authentication.getName());

		if (usuario.isEmpty() || usuario.get().getVeterinario() == null) {
			return false;
		}

		Veterinario vet = usuario.get().getVeterinario();

		return repConsulta.veterinarioAtendeResponsavel(vet.getId(), idResponsavel);
	}

	public boolean veterinarioAcessaAnimal(Authentication authentication, Long idAnimal) {
		Optional<Animal> animal = repAnimal.findById(idAnimal);

		if (animal.isEmpty()) {
			return false;
		}

		return veterinarioAcessaResponsavel(authentication, animal.get().getResponsavel().getId());
	}
}