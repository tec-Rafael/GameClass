package br.com.escola.escolacursoapi.repository;

import br.com.escola.escolacursoapi.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.matriculas m LEFT JOIN FETCH m.aluno LEFT JOIN FETCH c.professor WHERE c.id = :id")
    Optional<Curso> findByIdComMatriculas(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.matriculas m LEFT JOIN FETCH m.aluno LEFT JOIN FETCH c.professor")
    List<Curso> findAllComMatriculas();
}
