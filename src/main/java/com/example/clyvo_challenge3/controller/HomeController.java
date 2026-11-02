package com.example.clyvo_challenge3.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.clyvo_challenge3.model.Clinica;
import com.example.clyvo_challenge3.model.Consulta;
import com.example.clyvo_challenge3.model.Responsavel;
import com.example.clyvo_challenge3.model.StatusConsultaEnum;
import com.example.clyvo_challenge3.model.TipoUsuarioEnum;
import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.model.Veterinario;
import com.example.clyvo_challenge3.repository.ConsultaRepository;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository repUsuario;

    @Autowired
    private ConsultaRepository repConsulta;

    @GetMapping("/home")
    public ModelAndView paginaHome(Authentication authentication) {

        String username = authentication.getName();

        Optional<Usuario> opUsuario = repUsuario.findByUsername(username);
        if (opUsuario.isEmpty()) {
            return new ModelAndView("redirect:/login");
        }
        Usuario usuario = opUsuario.get();

        ModelAndView mv = new ModelAndView("home");

        if (usuario.getTipo() == TipoUsuarioEnum.CLINICA) {
            Clinica clinica = usuario.getClinica();
            mv.addObject("clinica", clinica);
        }

        if (usuario.getTipo() == TipoUsuarioEnum.VETERINARIO) {
            Veterinario veterinario = usuario.getVeterinario();
            mv.addObject("veterinario", veterinario);

            List<Consulta> consultas = repConsulta.findByVeterinario(veterinario.getId());

            // Tutores atendidos pela veterinaria, sem repeticao e em ordem alfabetica
            Map<Long, Responsavel> mapaTutores = new LinkedHashMap<>();
            for (Consulta consulta : consultas) {
                Responsavel tutor = consulta.getAnimal().getResponsavel();
                mapaTutores.putIfAbsent(tutor.getId(), tutor);
            }
            List<Responsavel> tutores = new ArrayList<>(mapaTutores.values());
            tutores.sort(Comparator.comparing(Responsavel::getNome, String.CASE_INSENSITIVE_ORDER));

            int totalAnimais = 0;
            for (Responsavel tutor : tutores) {
                totalAnimais += tutor.getAnimais().size();
            }

            // Consultas agendadas ou em andamento, da mais proxima para a mais distante
            List<Consulta> proximasConsultas = new ArrayList<>();
            for (Consulta consulta : consultas) {
                if (consulta.getStatusConsulta() == StatusConsultaEnum.FUTURA
                        || consulta.getStatusConsulta() == StatusConsultaEnum.EM_ANDAMENTO) {
                    proximasConsultas.add(consulta);
                }
            }
            proximasConsultas.sort(Comparator.comparing(Consulta::getDataConsulta));

            mv.addObject("tutores", tutores);
            mv.addObject("totalTutores", tutores.size());
            mv.addObject("totalAnimais", totalAnimais);
            mv.addObject("totalConsultas", consultas.size());
            mv.addObject("proximasConsultas", proximasConsultas);
        }

        return mv;
    }
}
