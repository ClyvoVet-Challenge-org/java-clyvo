package com.example.clyvo_challenge3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.br.CNPJ;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_CLINICA")
public class Clinica implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinica")
    private Long id;

    @NotBlank(message = "O CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido")
    @Column(name = "cnpj_clinica", nullable = false, unique = true, length = 20)
    private String cnpj;

    @NotBlank(message = "O nome da clínica é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(name = "nm_clinica", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @NotNull(message = "O status do contrato é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "st_contrato", length = 20, nullable = false)
    private StatusContratoEnum statusContrato;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
    private List<ClinicaVeterinario> veterinarios = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
    private List<EnderecoClinica> enderecos = new ArrayList<>();

    public Clinica() {
        super();
        this.statusContrato = StatusContratoEnum.ATIVO;
    }

    public Clinica(Long id, String cnpj, String nome, String email, String senha) {
        super();
        this.id = id;
        this.cnpj = cnpj;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.statusContrato = StatusContratoEnum.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusContratoEnum getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(StatusContratoEnum statusContrato) {
        this.statusContrato = statusContrato;
    }

    public List<ClinicaVeterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<ClinicaVeterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    public List<EnderecoClinica> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoClinica> enderecos) {
        this.enderecos = enderecos;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}


    
}