package br.com.escola.escolacursoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CursoRequestDTO {

    @NotBlank(message = "Titulo e obrigatorio")
    private String titulo;

    private String descricao;

    @NotNull(message = "ID do professor e obrigatorio")
    private Long professorId;

    public CursoRequestDTO() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }
}
