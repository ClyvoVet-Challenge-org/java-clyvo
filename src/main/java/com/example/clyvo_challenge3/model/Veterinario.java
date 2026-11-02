package com.example.clyvo_challenge3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.example.clyvo_challenge3.dto.request.VeterinarioEdicaoRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVOJ_VET")
public class Veterinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vet")
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Column(name = "nm_vet", nullable = false, length = 100)
	private String nome;

	@NotBlank(message = "O CPF é obrigatório")
	@CPF(message = "CPF inválido")
	@Column(name = "cpf_vet", nullable = false, unique = true, length = 14)
	private String cpf;

	@NotBlank(message = "O CRMV é obrigatório")
	@Pattern(regexp = "^[A-Z]{2}\\d{3,6}$", message = "CRMV deve estar no formato UFNNNNN (ex: SP1234)")
	@Size(max = 20, message = "O CRMV deve ter no máximo 20 caracteres")
	@Column(name = "crmv_vet", nullable = false, unique = true, length = 20)
	private String crmv;

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "Email inválido")
	@Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Column(name = "senha", nullable = false, length = 255)
	private String senha;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "veterinario")
	private List<ClinicaVeterinario> clinicas = new ArrayList<>();
    
    
    public Veterinario() {
        super();
    }

    public Veterinario(Long id, String nome, String cpf, String crmv, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.crmv = crmv;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
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
    
    

	public List<ClinicaVeterinario> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<ClinicaVeterinario> clinicas) {
		this.clinicas = clinicas;
	}

	public void atualizarVeterinario(VeterinarioEdicaoRequest veterinario) {
		this.nome = veterinario.nome();
        this.cpf = veterinario.cpf();
        this.crmv = veterinario.crmv();
	}
	
}