package br.com.escola.escolacursoapi.dto;

public class ProfessorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    // Nome do curso que este professor leciona (pode ser null se nao vinculado a nenhum curso).
    private String nomeCurso;

    public ProfessorResponseDTO(Long id, String nome, String email, String nomeCurso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nomeCurso = nomeCurso;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getNomeCurso() { return nomeCurso; }
}
