package com.example.clyvo_challenge3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.model.Clinica;
import com.example.clyvo_challenge3.model.TipoUsuarioEnum;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.repository.ClinicaRepository;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class ClinicaController {

	@Autowired
	private ClinicaRepository repClinica;

	@Autowired
	private UsuarioRepository repUsuario;

	@Autowired
	private PasswordEncoder passwordEncoder; // já é um Bean, graças ao SecurityConfig

	@GetMapping("/cadastro-clinica")
	public ModelAndView paginaCadastroClinica() {
		ModelAndView mv = new ModelAndView("clinica/novaClinica");
		mv.addObject("clinica", new Clinica());
		return mv;
	}

	@PostMapping("/cadastrar_clinica")
	public ModelAndView cadastrarClinica(@Valid Clinica clinica_form, BindingResult bd) {

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("clinica/novaClinica");
			mv.addObject("clinica", clinica_form);
			return mv;
		}

		// Guarda a senha em texto puro ANTES de virar hash,
		// pois vamos reaproveitá-la para o login (Usuario)
		String senhaDigitada = clinica_form.getSenha();

		// ESSENCIAL: nunca salvar senha em texto puro na Clinica
		clinica_form.setSenha(passwordEncoder.encode(senhaDigitada));

		Clinica clinica_banco = repClinica.save(clinica_form);

		// Cria o Usuario de login, vinculado a essa Clinica
		Usuario usuario = new Usuario();
		usuario.setUsername(clinica_banco.getEmail());
		usuario.setSenha(passwordEncoder.encode(senhaDigitada));
		usuario.setClinica(clinica_banco);
		usuario.setTipo(TipoUsuarioEnum.CLINICA);
		repUsuario.save(usuario);

		return new ModelAndView("redirect:/login");
	}

	@GetMapping("/clinica/perfil")
	public ModelAndView paginaPerfilClinica(Authentication auth) {
		ModelAndView mv = new ModelAndView("clinica/perfil");

		String username = auth.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		Clinica infoClinicaLogada = opUsuario.get().getClinica();
		mv.addObject("clinica", infoClinicaLogada);

		return mv;
	}

	@GetMapping("/clinica/edicao")
	public ModelAndView paginaEdicaoClinica(Authentication auth) {

		String username = auth.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		Clinica infoClinicaLogada = opUsuario.get().getClinica();

		ModelAndView mv = new ModelAndView("clinica/edicao");

		mv.addObject("clinica", infoClinicaLogada);

		return mv;
	}

	@PostMapping("/clinica/atualizar")
	public ModelAndView funcaoAtualizar(Authentication auth, @Valid Clinica clinicaForm, BindingResult bd) {

		String username = auth.getName();

		Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
		if (opUsuario.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("clinica/edicao");
			mv.addObject("clinica", clinicaForm);
			return mv;
		}

		Usuario usuario = opUsuario.get();
		Clinica clinicaLogada = usuario.getClinica();
		String hash = passwordEncoder.encode(clinicaForm.getSenha());

		// O e-mail e o login e nao e alterado aqui
		clinicaLogada.setNome(clinicaForm.getNome());
		clinicaLogada.setCnpj(clinicaForm.getCnpj());
		clinicaLogada.setSenha(hash);

		// Mantem a senha de login (tabela usuario) igual a da clinica
		usuario.setSenha(hash);
		repUsuario.save(usuario);

		repClinica.save(clinicaLogada);

		return new ModelAndView("redirect:/clinica/perfil");
	}
}
