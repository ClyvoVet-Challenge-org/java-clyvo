package com.example.clyvo_challenge3.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.clyvo_challenge3.model.Usuario;
import com.example.clyvo_challenge3.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repU;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = repU.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não existente"));

		return new User(usuario.getUsername(), usuario.getSenha(),
				Collections.singletonList(new SimpleGrantedAuthority(usuario.getTipo().name())));
	}
}