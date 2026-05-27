package br.com.escola.escolacursoapi.dto;

import br.com.escola.escolacursoapi.domain.NivelContaAluno;

public class AlunoResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String ra;
    private NivelContaAluno nivelConta;
    private int cursosAprovados;

    public AlunoResponseDTO(Long id, String nome, String email, String ra,
                            NivelContaAluno nivelConta, int cursosAprovados) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ra = ra;
        this.nivelConta = nivelConta;
        this.cursosAprovados = cursosAprovados;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRa() { return ra; }
    public NivelContaAluno getNivelConta() { return nivelConta; }
    public int getCursosAprovados() { return cursosAprovados; }
}
