package com.example.clyvo_challenge3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.model.Clinica;
import com.example.clyvo_challenge3.model.TipoUsuarioEnum;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.model.Veterinario;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository repUsuario;

    @GetMapping("/home")
    public ModelAndView paginaHome(Authentication authentication) {

        String username = authentication.getName();

        Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
        if (opUsuario.isEmpty()) {
            return new ModelAndView("redirect:/login");
        }
        Usuario usuario = opUsuario.get();
        
        ModelAndView mv = new ModelAndView("home");
        
        if (usuario.getTipo()==TipoUsuarioEnum.CLINICA) {
        	Clinica clinica = usuario.getClinica();
        	mv.addObject("clinica", clinica);
        }

        if (usuario.getTipo()==TipoUsuarioEnum.VETERINARIO) {
        	Veterinario veterinario= usuario.getVeterinario();
        	mv.addObject("veterinario", veterinario);
        }
        
        return mv;
    }
}