package br.com.escola.escolacursoapi.domain;

import br.com.escola.escolacursoapi.domain.vo.DescricaoCurso;
import br.com.escola.escolacursoapi.domain.vo.TituloCurso;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Camada: DOMINIO.
// Curso possui: titulo, descricao, professor e lista de matriculas.
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TituloCurso titulo;

    @Embedded
    private DescricaoCurso descricao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    protected Curso() {
    }

    public Curso(String titulo, String descricao, Professor professor) {
        this.titulo = new TituloCurso(titulo);
        this.descricao = new DescricaoCurso(descricao);
        vincularProfessor(professor);
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo.getValor(); }
    public String getDescricao() { return descricao != null ? descricao.getValor() : null; }
    public Professor getProfessor() { return professor; }
    public List<Matricula> getMatriculas() { return Collections.unmodifiableList(matriculas); }

    public void alterarTitulo(String titulo) { this.titulo = new TituloCurso(titulo); }
    public void alterarDescricao(String descricao) { this.descricao = new DescricaoCurso(descricao); }

    public void vincularProfessor(Professor professor) {
        if (this.professor != null) {
            this.professor.setCursoInterno(null);
        }
        this.professor = professor;
        if (professor != null) {
            professor.setCursoInterno(this);
        }
    }

    public void adicionarMatricula(Matricula matricula) {
        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
        }
    }

    public void removerMatricula(Matricula matricula) {
        matriculas.remove(matricula);
    }
}
