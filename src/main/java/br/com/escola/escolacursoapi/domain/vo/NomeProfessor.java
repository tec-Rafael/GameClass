package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NomeProfessor {

    @Column(name = "nome", nullable = false)
    private String valor;

    protected NomeProfessor() {
    }

    public NomeProfessor(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Nome do professor e obrigatorio");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeProfessor that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
