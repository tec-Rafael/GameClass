package br.com.escola.escolacursoapi.domain;

import br.com.escola.escolacursoapi.domain.vo.EmailAluno;
import br.com.escola.escolacursoapi.domain.vo.NomeAluno;
import br.com.escola.escolacursoapi.domain.vo.RaAluno;
import br.com.escola.escolacursoapi.domain.vo.SenhaCriptografada;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Camada: DOMINIO.
// Aluno possui dados de cadastro e nivel de conta.
// A nota nao fica mais aqui, pois cada nota pertence a uma matricula em um curso.
@Entity
@Table(name = "alunos")
public class Aluno {

    private static final int QUANTIDADE_APROVACOES_PARA_PREMIUM = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomeAluno nome;

    @Embedded
    private EmailAluno email;

    @Embedded
    private RaAluno ra;

    @Embedded
    private SenhaCriptografada senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelContaAluno nivelConta;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    protected Aluno() {
    }

    public Aluno(String nome, String email, String ra, String senhaCriptografada) {
        this.nome = new NomeAluno(nome);
        this.email = new EmailAluno(email);
        this.ra = new RaAluno(ra);
        this.senha = new SenhaCriptografada(senhaCriptografada);
        this.nivelConta = NivelContaAluno.BASICO;
    }

    public Long getId() { return id; }
    public String getNome() { return nome.getValor(); }
    public String getEmail() { return email.getValor(); }
    public String getRa() { return ra.getValor(); }
    public String getSenha() { return senha.getValor(); }
    public NivelContaAluno getNivelConta() { return nivelConta; }
    public List<Matricula> getMatriculas() { return Collections.unmodifiableList(matriculas); }

    public void alterarNome(String nome) { this.nome = new NomeAluno(nome); }
    public void alterarEmail(String email) { this.email = new EmailAluno(email); }
    public void alterarRa(String ra) { this.ra = new RaAluno(ra); }
    public void alterarSenhaCriptografada(String senhaCriptografada) {
        this.senha = new SenhaCriptografada(senhaCriptografada);
    }

    public void adicionarMatricula(Matricula matricula) {
        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
        }
    }

    public void removerMatricula(Matricula matricula) {
        matriculas.remove(matricula);
    }

    // Regra de negocio: ao chegar em 12 ou mais cursos aprovados, vira PREMIUM.
    // Se ficar abaixo disso, volta para BASICO, mantendo o estado sempre consistente.
    public void atualizarNivelConta(int quantidadeCursosAprovados) {
        this.nivelConta = quantidadeCursosAprovados >= QUANTIDADE_APROVACOES_PARA_PREMIUM
                ? NivelContaAluno.PREMIUM
                : NivelContaAluno.BASICO;
    }
}
