package br.com.escola.escolacursoapi.dto;

public class MatriculaResponseDTO {

    private Long id;
    private Long alunoId;
    private String nomeAluno;
    private String raAluno;
    private Long cursoId;
    private String tituloCurso;
    private Double nota;
    private String situacao;
    private String nivelContaAluno;

    public MatriculaResponseDTO(Long id, Long alunoId, String nomeAluno, String raAluno,
                                Long cursoId, String tituloCurso, Double nota,
                                String situacao, String nivelContaAluno) {
        this.id = id;
        this.alunoId = alunoId;
        this.nomeAluno = nomeAluno;
        this.raAluno = raAluno;
        this.cursoId = cursoId;
        this.tituloCurso = tituloCurso;
        this.nota = nota;
        this.situacao = situacao;
        this.nivelContaAluno = nivelContaAluno;
    }

    public Long getId() { return id; }
    public Long getAlunoId() { return alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public String getRaAluno() { return raAluno; }
    public Long getCursoId() { return cursoId; }
    public String getTituloCurso() { return tituloCurso; }
    public Double getNota() { return nota; }
    public String getSituacao() { return situacao; }
    public String getNivelContaAluno() { return nivelContaAluno; }
}
