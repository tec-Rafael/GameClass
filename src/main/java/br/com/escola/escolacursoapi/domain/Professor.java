package br.com.escola.escolacursoapi.domain;

import br.com.escola.escolacursoapi.domain.vo.EmailProfessor;
import br.com.escola.escolacursoapi.domain.vo.NomeProfessor;
import br.com.escola.escolacursoapi.domain.vo.SenhaCriptografada;
import jakarta.persistence.*;

// Camada: DOMINIO.
// Professor possui: nome, email, senha e o curso que leciona.
// A relacao com Curso e ManyToOne: um curso tem 1 professor, mas um professor pode ter varios cursos.
@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomeProfessor nome;

    @Embedded
    private EmailProfessor email;

    // SenhaCriptografada e o mesmo VO do Aluno.
    // Como o @Column esta com nome explicito, nao ha conflito de coluna.
    @Embedded
    private SenhaCriptografada senha;

    // Relacao bidirecional: Professor sabe qual Curso leciona.
    // O lado "dono" da FK esta no Curso (professor_id na tabela cursos).
    @OneToOne(mappedBy = "professor", fetch = FetchType.LAZY)
    private Curso curso;

    protected Professor() {
    }

    public Professor(String nome, String email, String senhaCriptografada) {
        this.nome = new NomeProfessor(nome);
        this.email = new EmailProfessor(email);
        this.senha = new SenhaCriptografada(senhaCriptografada);
    }

    public Long getId() { return id; }
    public String getNome() { return nome.getValor(); }
    public String getEmail() { return email.getValor(); }
    public String getSenha() { return senha.getValor(); }
    public Curso getCurso() { return curso; }

    public void alterarNome(String nome) { this.nome = new NomeProfessor(nome); }
    public void alterarEmail(String email) { this.email = new EmailProfessor(email); }
    public void alterarSenhaCriptografada(String senha) {
        this.senha = new SenhaCriptografada(senha);
    }

    // Metodo interno usado pelo Curso para manter a consistencia bidirecional.
    void setCursoInterno(Curso curso) {
        this.curso = curso;
    }
}
