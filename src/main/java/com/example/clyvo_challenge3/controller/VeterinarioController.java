package com.example.clyvo_challenge3.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.dto.request.VeterinarioEdicaoRequest;
import com.example.clyvo_challenge3.model.Clinica;
import com.example.clyvo_challenge3.model.ClinicaVeterinario;
import com.example.clyvo_challenge3.model.Consulta;
import com.example.clyvo_challenge3.model.TipoUsuarioEnum;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.model.Veterinario;
import com.example.clyvo_challenge3.repository.ClinicaVeterinarioRepository;
import com.example.clyvo_challenge3.repository.ConsultaRepository;
import com.example.clyvo_challenge3.repository.UsuarioRepository;
import com.example.clyvo_challenge3.repository.VeterinarioRepository;

import jakarta.validation.Valid;

@Controller
public class VeterinarioController {

	@Autowired
	private VeterinarioRepository repVet;

	@Autowired
	private UsuarioRepository repUsuario;
	
	@Autowired
	private ClinicaVeterinarioRepository repCliVet;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ConsultaRepository repConsulta;

//	// Não Uitilizado
//	@GetMapping("/vets")
//	public ModelAndView paginaTodosVeterinario() {
//		ModelAndView mv = new ModelAndView("veterinario/all");
//
//		List<Veterinario> vets = repVet.findAll();
//
//		mv.addObject("vets", vets);
//
//		return mv;
//	}

	// APENAS : CLINICA LOGADA
	@GetMapping("/clinica/veterinarios")
	public ModelAndView paginaListagemVeterinariosDaClinica(Authentication authentication) {

		// 1) Descobre qual Usuario esta logado (username = email da clinica)
		String username = authentication.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		// 2) Pega a Clinica vinculada a esse Usuario
		Usuario usuario = opUsuario.get();
		Clinica clinica = usuario.getClinica();

		// 3) Extrai a lista de Veterinario a partir dos vinculos ClinicaVeterinario
		List<Veterinario> veterinarios = clinica.getVeterinarios().stream().map(ClinicaVeterinario::getVeterinario)
				.collect(Collectors.toList());

		ModelAndView mv = new ModelAndView("clinica/veterinarios");
		mv.addObject("clinica", clinica);
		mv.addObject("veterinarios", veterinarios);

		return mv;
	}

//	@GetMapping("/vets/{id}")
//	public ModelAndView paginaDetalhes(@PathVariable Long id) {
//		ModelAndView mv = new ModelAndView("veterinario/detalhes");
//
//		Optional<Veterinario> op = repVet.findById(id);
//
//		if (op.isPresent()) {
//			Veterinario veterinario = op.get();
//			mv.addObject("vet", veterinario);
//			return mv;
//
//		}
//
//		// Usuario Inexistente
//		return new ModelAndView("redirect:/vets");
//	}

	// APENAS : CLINICA LOGADA
	@GetMapping("/clinica/veterinarios/{idVeterinario}")
	public ModelAndView paginaDetalhesVeterinarioDaClinica(Authentication authentication, @PathVariable Long idVeterinario) {
		ModelAndView mv = new ModelAndView("veterinario/detalhes");

		// 1) Descobre qual Usuario esta logado (username = email da clinica)
		String username = authentication.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		// 2) Pega a Clinica vinculada a esse Usuario
		Usuario usuario = opUsuario.get();
		Clinica clinica = usuario.getClinica();
		
		
		// 3) Confere se o veterinario da URL é contratado pela clinica logada
		Optional<ClinicaVeterinario> opCliVet = repCliVet.findByClinicaAndVeterinario(clinica.getId(),idVeterinario);
		
		if (opCliVet.isEmpty()) {return new ModelAndView("redirect:/clinica/veterinarios");}
		
		Veterinario veterinarioAssociado = opCliVet.get().getVeterinario();

		mv.addObject("vet", veterinarioAssociado);
		return mv;

	}
	
	// APENAS : VETERINARIO LOGADO
	@GetMapping("/vet/edicao")
	public ModelAndView paginaEdicaoVet(Authentication authentication) {

		String username = authentication.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);

		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}
		Veterinario veterinarioLogado = opUsuario.get().getVeterinario();

		ModelAndView mv = new ModelAndView("veterinario/edicao");
		mv.addObject("veterinario", veterinarioLogado);

		return mv;

	};
	
	// APENAS : VETERINARIO LOGADO
	@PostMapping("/vet/atualizar")
	public ModelAndView funcaoAtualizarVeterinario(Authentication authentication, @Valid @ModelAttribute("veterinario") VeterinarioEdicaoRequest veterinarioForm,
			BindingResult bd) {

		String username = authentication.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);

		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		Usuario usuario = opUsuario.get();

		Veterinario veterinarioLogado = usuario.getVeterinario();

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("veterinario/edicao");

			mv.addObject("veterinario", veterinarioForm);

			return mv;
		}
		
		

		veterinarioLogado.atualizarVeterinario(veterinarioForm);

		repVet.save(veterinarioLogado);

		return new ModelAndView("redirect:/vet/perfil");

	};
	
	//APENAS : VETERINARIO LOGADO
	@GetMapping("/vet/perfil")
	public ModelAndView paginaPerfilVeterinario(Authentication authentication) {

	    String username = authentication.getName();

	    Optional<Usuario> opUsuario =
	            repUsuario.findByUsername(username);

	    if (opUsuario.isEmpty()) {
	        return new ModelAndView("redirect:/login");
	    }

	    Veterinario veterinarioLogado =
	            opUsuario.get().getVeterinario();

	    ModelAndView mv =
	            new ModelAndView("veterinario/perfil");

	    mv.addObject("veterinario", veterinarioLogado);

	    return mv;
	}
	
	// APENAS : CLINICA LOGADA
	@GetMapping("/clinica/veterinarios/novo")
	public ModelAndView paginaCadastroVeterinario(Authentication authentication) {

		String username = authentication.getName();
		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);

		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		Clinica clinica = opUsuario.get().getClinica();

		ModelAndView mv = new ModelAndView("veterinario/novoVeterinario");
		mv.addObject("clinica", clinica);
		mv.addObject("veterinario", new Veterinario());

		return mv;
	}
	
	// APENAS : CLINICA LOGADO
	@PostMapping("/clinica/veterinarios/salvar")
	public ModelAndView funcaoAssociarVeterinarioEClinica(Authentication authentication, @Valid Veterinario veterinario,BindingResult bd) {
		
		
		String username = authentication.getName();
		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);

		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}
		
		Clinica clinica = opUsuario.get().getClinica();
		
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("veterinario/novoVeterinario");
			mv.addObject("veterinario", veterinario);
			mv.addObject("clinica",clinica);
			return mv;
		}
		
		Veterinario veterinarioSalvo = cadastrarVeterinarioComLogin(veterinario);
		
		ClinicaVeterinario clivet = new ClinicaVeterinario();
		clivet.setClinica(clinica);
		clivet.setVeterinario(veterinarioSalvo);
		
		repCliVet.save(clivet);
		
		return new ModelAndView("redirect:/clinica/veterinarios");
	}
	
	// TODOS PODEM ACESSAR
	@GetMapping("/cadastro-veterinario")
	public ModelAndView paginaCadastroVeterinario() {
	    ModelAndView mv = new ModelAndView("veterinario/novoVeterinario");
	    mv.addObject("veterinario", new Veterinario());
	    return mv;
	}
	
	@PostMapping("/cadastrar-veterinario")
	public ModelAndView cadastrarVeterinario(@Valid Veterinario veterinario_form, BindingResult bd) {

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("veterinario/novoVeterinario");
			mv.addObject("veterinario", veterinario_form);
			return mv;
		}

		cadastrarVeterinarioComLogin(veterinario_form);

		return new ModelAndView("redirect:/login");
	}
	
	// APENAS: VETERINARIO
	@GetMapping("/vet/minhas-consultas")
	public ModelAndView paginaConsultasDoVeterinario(Authentication authentication) {
		ModelAndView mv = new ModelAndView("veterinario/consultas");
		String username = authentication.getName();
		
		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {return new ModelAndView("redirect:/login");}
		
		Veterinario veterinarioLogado = opUsuario.get().getVeterinario();
		
		List<Consulta> listaConsultas= repConsulta.findByVeterinario(veterinarioLogado.getId());
		
		
		mv.addObject("consultas",listaConsultas);

		return mv;
		}
	
	// Salva o veterinario com senha criptografada e cria o usuario usado no login
	private Veterinario cadastrarVeterinarioComLogin(Veterinario veterinario) {
		String hash = passwordEncoder.encode(veterinario.getSenha());
		veterinario.setSenha(hash);

		Veterinario salvo = repVet.save(veterinario);

		Usuario usuario = new Usuario();
		usuario.setUsername(salvo.getEmail());
		usuario.setSenha(hash);
		usuario.setTipo(TipoUsuarioEnum.VETERINARIO);
		usuario.setVeterinario(salvo);
		repUsuario.save(usuario);

		return salvo;
	}

}
