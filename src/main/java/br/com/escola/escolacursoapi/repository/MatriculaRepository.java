package br.com.escola.escolacursoapi.repository;

import br.com.escola.escolacursoapi.domain.Matricula;
import br.com.escola.escolacursoapi.domain.SituacaoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByCursoIdAndAlunoId(Long cursoId, Long alunoId);

    Optional<Matricula> findByCursoIdAndAlunoId(Long cursoId, Long alunoId);

    int countByAlunoIdAndSituacao(Long alunoId, SituacaoAluno situacao);

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.curso")
    List<Matricula> findAllComDados();

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.curso WHERE m.curso.id = :cursoId")
    List<Matricula> findByCursoIdComDados(@Param("cursoId") Long cursoId);

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.curso WHERE m.curso.id = :cursoId AND m.aluno.id = :alunoId")
    Optional<Matricula> findByCursoIdAndAlunoIdComDados(@Param("cursoId") Long cursoId,
                                                        @Param("alunoId") Long alunoId);
}
