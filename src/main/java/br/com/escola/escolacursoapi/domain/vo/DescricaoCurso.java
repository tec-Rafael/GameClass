package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DescricaoCurso {

    @Column(name = "descricao")
    private String valor;

    protected DescricaoCurso() {
    }

    public DescricaoCurso(String valor) {
        this.valor = valor != null ? valor.trim() : null;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescricaoCurso that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
