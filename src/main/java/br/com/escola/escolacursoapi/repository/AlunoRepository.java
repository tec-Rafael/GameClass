package br.com.escola.escolacursoapi.repository;

import br.com.escola.escolacursoapi.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByEmailValor(String email);
    boolean existsByRaValor(String ra);
}
