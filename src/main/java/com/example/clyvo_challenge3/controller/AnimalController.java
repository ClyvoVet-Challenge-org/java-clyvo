package com.example.clyvo_challenge3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.dto.AnimalEdicaoDTO;
import com.example.clyvo_challenge3.dto.EnderecoAnimalEdicaoDTO;
import com.example.clyvo_challenge3.model.Animal;
import com.example.clyvo_challenge3.model.EnderecoAnimal;
import com.example.clyvo_challenge3.model.Responsavel;
import com.example.clyvo_challenge3.repository.AnimalRepository;
import com.example.clyvo_challenge3.repository.ResponsavelRepository;
import com.example.clyvo_challenge3.service.AcessoService;

import jakarta.validation.Valid;

@Controller
public class AnimalController {

	@Autowired
	private AnimalRepository repAnimal;

	@Autowired
	private ResponsavelRepository repResponsavel;
	
	@Autowired
	private AcessoService acesso;

//	@GetMapping("/animais")
//	public ModelAndView paginaListagemAnimais() {
//		ModelAndView mv = new ModelAndView("animal/all");
//
//		List<Animal> animais = repAnimal.findAll();
//		mv.addObject("animais", animais);
//
//		return mv;
//	}

	@GetMapping("/animais/{id}")
	public ModelAndView paginaDetalhes(Authentication authentication, @PathVariable Long id) {
		ModelAndView mv = new ModelAndView("animal/detalhes");

		Optional<Animal> op = repAnimal.findById(id);

		if (op.isPresent()) {
			// Não é seu paciente
			if (!acesso.veterinarioAcessaAnimal(authentication, id)) {
				return new ModelAndView("redirect:/acesso-negado");
			}
			
			
			Animal animal = op.get();
			mv.addObject("animal", animal);
			mv.addObject("vacinacoes", animal.getVacinacoes());
			return mv;
		}

		// Animal inexistente
		return new ModelAndView("redirect:/home");
	}

	// =============== CREATE ==================

	// FALTA AUTENTICACAO
	@GetMapping("/responsaveis/{idResponsavel}/animais/novo")
	public ModelAndView paginaCadastroAnimalTeste(Authentication authentication, @PathVariable Long idResponsavel) {

		// OBJETIVO : CRIAR UM NOVO ANIMAL
		// CONFIRMAR SE O ID DO RESPONSAVEL(URL) EXISTE
		// SE NÃO EXISTIR -> MANDAR PARA ?
		// SE EXISTIR ->
		// INSTACIAR O OBJETO RESPONSAVEL PARA SER UTILIZADO NA PÁGINA
		// CRIAR UM OBJETO DE ANIMAL PARA RECEBER OS VALORES DO FORMAULARIO

		Optional<Responsavel> opResponsavel = repResponsavel.findById(idResponsavel);

		// Esse id existe no banco?
		if (opResponsavel.isEmpty()) {
			ModelAndView mv = new ModelAndView("redirect:/home");
			return mv;
		}
		
		if (!acesso.veterinarioAcessaResponsavel(authentication, idResponsavel)) {
			return new ModelAndView("redirect:/acesso-negado");
		}

		// Esse id é o mesmo que fez a login e se autenticou?

		Responsavel responsavel = opResponsavel.get();

		ModelAndView mv = new ModelAndView("animal/novoAnimal");

		mv.addObject("responsavel", responsavel);

		Animal animal = new Animal();
		animal.setResponsavel(responsavel);
		mv.addObject("animal", animal);

		return mv;
	}

	// FALTA AUTENTICACAO
	@PostMapping("/responsaveis/{idResponsavel}/animais/salvar") // action do formulario de animal
	public ModelAndView cadastrarAnimal(Authentication authentication,@PathVariable Long idResponsavel, @Valid Animal animal_form, BindingResult bd) {

		Optional<Responsavel> opResponsavel = repResponsavel.findById(idResponsavel);
		// Esse id existe?
		if (opResponsavel.isEmpty()) {
			return new ModelAndView("redirect:/home");
		}
		
		if (!acesso.veterinarioAcessaResponsavel(authentication, idResponsavel)) {
			return new ModelAndView("redirect:/acesso-negado");
		}

		Responsavel responsavel = opResponsavel.get();

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("animal/novoAnimal");
			mv.addObject("animal", animal_form);
			mv.addObject("responsavel", responsavel);
			System.out.println("Deu Erro");
			return mv;
		}

		animal_form.setResponsavel(responsavel);

		if (animal_form.getEnderecoAnimal() != null) {
			animal_form.getEnderecoAnimal().setAnimal(animal_form);
		}

		Animal animal_banco = repAnimal.save(animal_form);

		System.out.println("Salvou no banco de dados");

		ModelAndView mv = new ModelAndView("redirect:/animais/" + animal_banco.getId());

		return mv;
	}

	// =============== UPDATE ===================

	@GetMapping("/responsaveis/animais/edicao/{idAnimal}")
	public ModelAndView paginaEdicaoAnimal(Authentication authentication,@PathVariable Long idAnimal) {

	    Optional<Animal> opAnimal = repAnimal.findById(idAnimal);
	    if (opAnimal.isEmpty()) {
	        return new ModelAndView("redirect:/home");
	    }
	    
		if (!acesso.veterinarioAcessaAnimal(authentication, idAnimal)) {
			return new ModelAndView("redirect:/acesso-negado");
		}
	    
	    Animal animal = opAnimal.get();

	    // Converte a ENTIDADE para o DTO, só para preencher o formulário
	    AnimalEdicaoDTO animalForm = new AnimalEdicaoDTO();
	    animalForm.setNome(animal.getNome());
	    animalForm.setDataNascimento(animal.getDataNascimento());
	    animalForm.setPeso(animal.getPeso());
	    animalForm.setEspecie(animal.getEspecie());
	    animalForm.setRaca(animal.getRaca());
	    animalForm.setRg(animal.getRg());
	    animalForm.setNumeroMicrochip(animal.getNumeroMicrochip());

	    EnderecoAnimalEdicaoDTO enderecoForm = new EnderecoAnimalEdicaoDTO();
	    // Animal pode nao ter endereco cadastrado: nesse caso o formulario vem vazio
	    EnderecoAnimal enderecoAtual = animal.getEnderecoAnimal();
	    if (enderecoAtual != null) {
	        enderecoForm.setPais(enderecoAtual.getPais());
	        enderecoForm.setEstado(enderecoAtual.getEstado());
	        enderecoForm.setCidade(enderecoAtual.getCidade());
	        enderecoForm.setBairro(enderecoAtual.getBairro());
	        enderecoForm.setLogradouro(enderecoAtual.getLogradouro());
	        enderecoForm.setNumeroRua(enderecoAtual.getNumeroRua());
	        enderecoForm.setComplemento(enderecoAtual.getComplemento());
	        enderecoForm.setCep(enderecoAtual.getCep());
	    }
	    animalForm.setEnderecoAnimal(enderecoForm);

	    ModelAndView mv = new ModelAndView("animal/edicao");
	    mv.addObject("animal", animal);
	    mv.addObject("animalForm", animalForm);
	    System.out.println("PÁGINA DE EDIÇÃO DE ANIMAL CARREGADA");
	    return mv;
	}

	@PostMapping("/responsaveis/animais/atualizar/{idAnimal}")
	public ModelAndView funcaoAtualizarAnimal(Authentication authentication, @PathVariable Long idAnimal,
			@Valid AnimalEdicaoDTO animal_form, BindingResult bd) {

		// O animal existe?
		Optional<Animal> opAnimal = repAnimal.findById(idAnimal);
		if (opAnimal.isEmpty()) {
			return new ModelAndView("redirect:/home");
		}

		// O veterinario logado atende o responsavel deste animal?
		if (!acesso.veterinarioAcessaAnimal(authentication, idAnimal)) {
			return new ModelAndView("redirect:/acesso-negado");
		}

		Animal animal_banco = opAnimal.get();

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("animal/edicao");
			mv.addObject("animal", animal_banco); // tem responsavel, usado so para exibir o nome dele
			mv.addObject("animalForm", animal_form); // dados digitados, para reexibir nos campos com erro
			return mv;
		}

		// Copia os campos basicos do animal
		animal_banco.setNome(animal_form.getNome());
		animal_banco.setDataNascimento(animal_form.getDataNascimento());
		animal_banco.setPeso(animal_form.getPeso());
		animal_banco.setEspecie(animal_form.getEspecie());
		animal_banco.setRaca(animal_form.getRaca());
		animal_banco.setRg(animal_form.getRg());
		animal_banco.setNumeroMicrochip(animal_form.getNumeroMicrochip());

		// Copia para a entidade EnderecoAnimal que ja existe em animal_banco
		EnderecoAnimal enderecoAnimal = animal_banco.getEnderecoAnimal();
		if (enderecoAnimal == null) {
			// Animal ainda sem endereco: cria um novo vinculado a ele
			enderecoAnimal = new EnderecoAnimal();
			enderecoAnimal.setAnimal(animal_banco);
			animal_banco.setEnderecoAnimal(enderecoAnimal);
		}
		enderecoAnimal.setPais(animal_form.getEnderecoAnimal().getPais());
		enderecoAnimal.setEstado(animal_form.getEnderecoAnimal().getEstado());
		enderecoAnimal.setCidade(animal_form.getEnderecoAnimal().getCidade());
		enderecoAnimal.setBairro(animal_form.getEnderecoAnimal().getBairro());
		enderecoAnimal.setLogradouro(animal_form.getEnderecoAnimal().getLogradouro());
		enderecoAnimal.setNumeroRua(animal_form.getEnderecoAnimal().getNumeroRua());
		enderecoAnimal.setComplemento(animal_form.getEnderecoAnimal().getComplemento());
		enderecoAnimal.setCep(animal_form.getEnderecoAnimal().getCep());

		repAnimal.save(animal_banco);

		return new ModelAndView("redirect:/animais/" + animal_banco.getId());
	}

	// =============== DELETE ===================

	// Exclui um animal que ainda nao possui consultas.
	// Endereco e vacinacoes do animal sao removidos junto (CascadeType.REMOVE no Animal).
	@PostMapping("/responsaveis/animais/remover/{idAnimal}")
	@Transactional
	public ModelAndView funcaoRemoverAnimal(Authentication authentication, @PathVariable Long idAnimal,
			@RequestParam(required = false) String origem) {

		// O animal existe?
		Optional<Animal> opAnimal = repAnimal.findById(idAnimal);
		if (opAnimal.isEmpty()) {
			return new ModelAndView("redirect:/home");
		}

		// O veterinario logado atende o responsavel deste animal?
		if (!acesso.veterinarioAcessaAnimal(authentication, idAnimal)) {
			return new ModelAndView("redirect:/acesso-negado");
		}

		Animal animal = opAnimal.get();
		Long idResponsavel = animal.getResponsavel().getId();

		// Animal com historico de consultas nao pode ser excluido
		if (!animal.getConsultas().isEmpty()) {
			return new ModelAndView("redirect:/animais/" + idAnimal + "?erroExclusao");
		}

		repAnimal.delete(animal);
		System.out.println("Animal " + idAnimal + " removido do banco de dados");

		// Volta para a tela de onde o usuario clicou em Excluir
		if ("home".equals(origem)) {
			return new ModelAndView("redirect:/home?excluido");
		}
		return new ModelAndView("redirect:/responsaveis/" + idResponsavel);
	}

}