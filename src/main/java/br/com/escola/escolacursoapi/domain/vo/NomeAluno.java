package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NomeAluno {

    @Column(name = "nome", nullable = false)
    private String valor;

    protected NomeAluno() {
    }

    public NomeAluno(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Nome do aluno e obrigatorio");
        }
        if (valor.trim().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeAluno that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
