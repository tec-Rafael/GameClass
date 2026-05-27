package br.com.escola.escolacursoapi.domain;

import jakarta.persistence.*;

// Camada: DOMINIO.
// Representa a matricula de um aluno em um curso.
// A nota e a situacao pertencem a esta relacao, nao ao aluno diretamente.
@Entity
@Table(
        name = "matriculas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "curso_id"})
)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column
    private Double nota;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoAluno situacao;

    protected Matricula() {
    }

    public Matricula(Aluno aluno, Curso curso) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno e obrigatorio para a matricula");
        }
        if (curso == null) {
            throw new IllegalArgumentException("Curso e obrigatorio para a matricula");
        }
        this.aluno = aluno;
        this.curso = curso;
        this.situacao = SituacaoAluno.EM_ANDAMENTO;
    }

    public Long getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public Curso getCurso() { return curso; }
    public Double getNota() { return nota; }
    public SituacaoAluno getSituacao() { return situacao; }

    // Regra de negocio: aprovado com nota >= 7.
    public void aplicarNota(Double nota) {
        this.nota = nota;
        this.situacao = (nota != null && nota >= 7.0)
                ? SituacaoAluno.APROVADO
                : SituacaoAluno.REPROVADO;
    }

    public boolean estaAprovada() {
        return SituacaoAluno.APROVADO.equals(situacao);
    }
}
