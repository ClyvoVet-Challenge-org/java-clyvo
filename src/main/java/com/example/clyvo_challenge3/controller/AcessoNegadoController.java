package com.example.clyvo_challenge3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcessoNegadoController {
	
	@GetMapping("/acesso-negado")
	public ModelAndView paginaAcessoNegado () {
		return new ModelAndView("acesso-negado");
		
	}

}
