package br.com.escola.escolacursoapi.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

// Value Object para a senha ja criptografada.
// A criptografia acontece antes, na camada de servico, via PasswordEncoder.
@Embeddable
public class SenhaCriptografada {

    @Column(name = "senha", nullable = false)
    private String valor;

    protected SenhaCriptografada() {
    }

    public SenhaCriptografada(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Senha e obrigatoria");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SenhaCriptografada that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
