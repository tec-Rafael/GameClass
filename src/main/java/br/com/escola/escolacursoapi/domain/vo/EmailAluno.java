package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EmailAluno {

    @Column(name = "email", nullable = false, unique = true)
    private String valor;

    protected EmailAluno() {
    }

    public EmailAluno(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Email do aluno e obrigatorio");
        }
        String normalizado = valor.trim().toLowerCase();
        if (!normalizado.contains("@")) {
            throw new IllegalArgumentException("Email invalido");
        }
        this.valor = normalizado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAluno that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
