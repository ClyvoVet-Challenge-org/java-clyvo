package com.example.clyvo_challenge3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.model.Animal;
import com.example.clyvo_challenge3.model.Responsavel;
import com.example.clyvo_challenge3.repository.ResponsavelRepository;
import com.example.clyvo_challenge3.service.AcessoService;

import jakarta.validation.Valid;


@Controller
public class ResponsavelController {
	
	@Autowired
	private ResponsavelRepository repResponsavel;
	
	@Autowired
	private AcessoService acesso;
	

	
//	@GetMapping("/responsaveis")
//	public ModelAndView paginaListagemResponsaveis() {
//		ModelAndView mv = new ModelAndView("responsavel/all");
//		 
//		List<Responsavel> responsaveis = repResponsavel.findAll();
//		mv.addObject("responsaveis",responsaveis);
//		
//		
//		return mv;
//	}

	@GetMapping("/responsaveis/{id}")
	public ModelAndView paginaDetalhes(Authentication authentication,@PathVariable Long id){
		ModelAndView mv = new ModelAndView("responsavel/detalhes");
		
		Optional<Responsavel> op = repResponsavel.findById(id);
		

		if (op.isPresent()) {
			
			if (!acesso.veterinarioAcessaResponsavel(authentication, id)) {
				return new ModelAndView("redirect:/acesso-negado");
			}
			
			Responsavel responsavel = op.get();
			mv.addObject("responsavel",responsavel);
			List<Animal> animais = responsavel.getAnimais();
			mv.addObject("animais",animais);			
			return mv;
			
		}
		
		// Usuario Inexistente
		return new ModelAndView("redirect:/home");
	}
	
//	@GetMapping("/cadastro-responsavel")
//	public ModelAndView paginaCadastroResponsavel() {
//		ModelAndView mv = new ModelAndView("responsavel/novoResponsavel");
// 
//		Responsavel responsavel = new Responsavel();
//		responsavel.setEnderecoResponsavel(new EnderecoResponsavel());
// 
//		mv.addObject("responsavel", responsavel);
// 
//		return mv;
//	}
	
//	@PostMapping("/cadastrar_responsavel") // action do formulario de responsavel
//	public ModelAndView cadastrarResponsavel(@Valid Responsavel responsavel_form, BindingResult bd) {
//		
//		
//		if(bd.hasErrors()) {
//			ModelAndView mv = new ModelAndView("responsavel/novoResponsavel");
//			mv.addObject("responsavel", responsavel_form);
//			System.out.println("Deu Erro");
//			return mv;
//		}
//		responsavel_form.getEnderecoResponsavel().setResponsavel(responsavel_form);
//		
//		Responsavel responsavel_banco = repResponsavel.save(responsavel_form);
//		
//		
//		System.out.println("Salvou no banco de dados");
//		
//		
//		ModelAndView mv = new ModelAndView("redirect:/responsaveis/"+responsavel_banco.getId());	
//		
//		return mv;
//		
//	}
	
//	@GetMapping("/responsaveis/edicao/{id}")
//	public ModelAndView paginaEdicaoResponsavel(@PathVariable Long id) {
//		
//		
//		Optional<Responsavel> op = repResponsavel.findById(id);
//		
//		if (op.isPresent()) {
//		
//			ModelAndView mv = new ModelAndView("responsavel/edicao");
//			mv.addObject("responsavel", op.get());
//	
//			return mv;
//		}
//		
//		return new ModelAndView("redirect:/responsaveis");
//	}
	
	@PostMapping("/responsaveis/atualizar/{id}")
	public ModelAndView funcaoAtualizarResponsavel(Authentication authentication,@PathVariable Long id,@Valid Responsavel responsavel, BindingResult bd) {
		
		
		Optional<Responsavel> op = repResponsavel.findById(id);
		if (op.isPresent()) {
			
			if (!acesso.veterinarioAcessaResponsavel(authentication, id)) {
				return new ModelAndView("redirect:/acesso-negado");
			}

			
			Responsavel responsavel_banco = op.get();

			if(bd.hasErrors()) {
				ModelAndView mv = new ModelAndView("responsavel/edicao");
				mv.addObject("responsavel", responsavel);
				System.out.println("Deu Erro");
				return mv;
			}
			
			responsavel_banco.transferirResponsavel(responsavel);
			repResponsavel.save(responsavel_banco);
			
			return new ModelAndView("redirect:/responsaveis/"+responsavel_banco.getId());
		}
		
		
		return new ModelAndView("redirect:/home");
		
	}
//																NÃO PERMITIDO
//	@GetMapping("/responsaveis/remover/{id}")
//	public ModelAndView funcaoRemoverResponsavel(@PathVariable Long id) {
//		Optional<Responsavel> op = repResponsavel.findById(id);
//		 
//		if (op.isPresent()) {
//			repResponsavel.deleteById(id);
//		}
//		
//		return new ModelAndView("redirect:/responsaveis");
//	}

}
