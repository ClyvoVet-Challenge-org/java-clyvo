package com.example.clyvo_challenge3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.authorizeHttpRequests(auth -> auth
						// Paginas publicas (login e cadastros)
						.requestMatchers("/", "/login", "/error",
								"/cadastro-clinica", "/cadastrar_clinica",
								"/cadastro-veterinario", "/cadastrar-veterinario").permitAll()
						// Areas separadas por perfil
						.requestMatchers("/clinica/**").hasAuthority("CLINICA")
						.requestMatchers("/vet/**", "/animais/**", "/responsaveis/**").hasAuthority("VETERINARIO")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home", true))
				.logout(config -> config.logoutSuccessUrl("/login"))
				.exceptionHandling(exception -> exception.accessDeniedHandler((request, response, e) -> {
					response.sendRedirect("/acesso-negado");
				}))
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
