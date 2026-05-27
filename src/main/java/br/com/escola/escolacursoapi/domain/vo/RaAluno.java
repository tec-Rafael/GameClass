package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RaAluno {

    @Column(name = "ra", nullable = false, unique = true)
    private String valor;

    protected RaAluno() {
    }

    public RaAluno(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("RA invalido");
        }
        if (valor.trim().length() < 5) {
            throw new IllegalArgumentException("RA deve possuir pelo menos 5 caracteres");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RaAluno that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
