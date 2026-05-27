package br.com.escola.escolacursoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AlunoRequestDTO {

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @Email(message = "Email invalido")
    @NotBlank(message = "Email e obrigatorio")
    private String email;

    @NotBlank(message = "RA e obrigatorio")
    private String ra;

    @NotBlank(message = "Senha e obrigatoria")
    private String senha;

    public AlunoRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRa() { return ra; }
    public void setRa(String ra) { this.ra = ra; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
