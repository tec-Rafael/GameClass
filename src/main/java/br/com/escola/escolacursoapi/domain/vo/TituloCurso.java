package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TituloCurso {

    @Column(name = "titulo", nullable = false)
    private String valor;

    protected TituloCurso() {
    }

    public TituloCurso(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Titulo do curso e obrigatorio");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TituloCurso that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
