package com.example.clyvo_challenge3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Endereco raiz: manda para a home.
    // Se o usuario nao estiver logado, o Spring Security leva para o login.
    @GetMapping("/")
    public String paginaInicial() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }
}
