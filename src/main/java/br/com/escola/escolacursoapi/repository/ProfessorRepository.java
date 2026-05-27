package br.com.escola.escolacursoapi.repository;

import br.com.escola.escolacursoapi.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByEmailValor(String email);
}
