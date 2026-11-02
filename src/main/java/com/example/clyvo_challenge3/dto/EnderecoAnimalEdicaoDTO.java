package com.example.clyvo_challenge3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoAnimalEdicaoDTO {

    @NotBlank(message = "O país é obrigatório")
    @Size(max = 20, message = "O país deve ter no máximo 20 caracteres")
    private String pais;

    @NotBlank(message = "O estado é obrigatório")
    @Size(max = 20, message = "O estado deve ter no máximo 20 caracteres")
    private String estado;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 20, message = "A cidade deve ter no máximo 20 caracteres")
    private String cidade;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 20, message = "O bairro deve ter no máximo 20 caracteres")
    private String bairro;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 50, message = "O logradouro deve ter no máximo 50 caracteres")
    private String logradouro;

    @NotBlank(message = "O número da rua é obrigatório")
    @Size(max = 15, message = "O número da rua deve ter no máximo 15 caracteres")
    private String numeroRua;

    @Size(max = 20, message = "O complemento deve ter no máximo 20 caracteres")
    private String complemento;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
    private String cep;

    public EnderecoAnimalEdicaoDTO() {}

    // getters e setters
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumeroRua() { return numeroRua; }
    public void setNumeroRua(String numeroRua) { this.numeroRua = numeroRua; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}