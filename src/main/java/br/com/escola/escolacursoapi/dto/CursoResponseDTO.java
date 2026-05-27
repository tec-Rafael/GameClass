package br.com.escola.escolacursoapi.dto;

import java.util.List;

public class CursoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Long professorId;
    private String nomeProfessor;
    private List<AlunoResumoDTO> alunos;

    public CursoResponseDTO(Long id, String titulo, String descricao,
                            Long professorId, String nomeProfessor,
                            List<AlunoResumoDTO> alunos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.professorId = professorId;
        this.nomeProfessor = nomeProfessor;
        this.alunos = alunos;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Long getProfessorId() { return professorId; }
    public String getNomeProfessor() { return nomeProfessor; }
    public List<AlunoResumoDTO> getAlunos() { return alunos; }

    // A nota e a situacao sao especificas da matricula do aluno neste curso.
    public record AlunoResumoDTO(Long id, String nome, String ra, Double nota,
                                 String situacao, String nivelConta) {}
}
