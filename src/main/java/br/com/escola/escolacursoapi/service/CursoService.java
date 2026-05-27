package br.com.escola.escolacursoapi.service;

import br.com.escola.escolacursoapi.domain.Aluno;
import br.com.escola.escolacursoapi.domain.Curso;
import br.com.escola.escolacursoapi.domain.Matricula;
import br.com.escola.escolacursoapi.domain.Professor;
import br.com.escola.escolacursoapi.domain.SituacaoAluno;
import br.com.escola.escolacursoapi.dto.AplicarNotaRequestDTO;
import br.com.escola.escolacursoapi.dto.CursoRequestDTO;
import br.com.escola.escolacursoapi.dto.CursoResponseDTO;
import br.com.escola.escolacursoapi.repository.AlunoRepository;
import br.com.escola.escolacursoapi.repository.CursoRepository;
import br.com.escola.escolacursoapi.repository.MatriculaRepository;
import br.com.escola.escolacursoapi.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public CursoService(CursoRepository cursoRepository,
                        ProfessorRepository professorRepository,
                        AlunoRepository alunoRepository,
                        MatriculaRepository matriculaRepository) {
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarTodos() {
        return cursoRepository.findAllComMatriculas().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findByIdComMatriculas(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + id));
        return toDTO(curso);
    }

    @Transactional
    public CursoResponseDTO criar(CursoRequestDTO dto) {
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor nao encontrado: id=" + dto.getProfessorId()));

        Curso curso = new Curso(dto.getTitulo(), dto.getDescricao(), professor);
        return toDTO(cursoRepository.save(curso));
    }

    @Transactional
    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = cursoRepository.findByIdComMatriculas(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + id));

        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor nao encontrado: id=" + dto.getProfessorId()));

        curso.alterarTitulo(dto.getTitulo());
        curso.alterarDescricao(dto.getDescricao());
        curso.vincularProfessor(professor);

        return toDTO(cursoRepository.save(curso));
    }

    @Transactional
    public CursoResponseDTO adicionarAluno(Long cursoId, Long alunoId) {
        Curso curso = cursoRepository.findByIdComMatriculas(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + cursoId));
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado: id=" + alunoId));

        if (matriculaRepository.existsByCursoIdAndAlunoId(cursoId, alunoId)) {
            throw new RuntimeException("Aluno ja esta matriculado neste curso");
        }

        Matricula matricula = new Matricula(aluno, curso);
        matriculaRepository.save(matricula);

        return toDTO(cursoRepository.findByIdComMatriculas(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + cursoId)));
    }

    @Transactional
    public CursoResponseDTO removerAluno(Long cursoId, Long alunoId) {
        Curso curso = cursoRepository.findByIdComMatriculas(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + cursoId));
        Matricula matricula = matriculaRepository.findByCursoIdAndAlunoIdComDados(cursoId, alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno nao esta matriculado neste curso"));

        Aluno aluno = matricula.getAluno();
        curso.removerMatricula(matricula);
        aluno.removerMatricula(matricula);
        matriculaRepository.delete(matricula);
        atualizarNivelContaAluno(aluno);

        return toDTO(curso);
    }

    @Transactional
    public CursoResponseDTO aplicarNota(Long cursoId, Long alunoId, AplicarNotaRequestDTO dto) {
        Curso curso = cursoRepository.findByIdComMatriculas(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + cursoId));

        if (curso.getProfessor() == null || !curso.getProfessor().getId().equals(dto.getProfessorId())) {
            throw new RuntimeException("Apenas o professor responsavel pelo curso pode aplicar nota");
        }

        Matricula matricula = matriculaRepository.findByCursoIdAndAlunoIdComDados(cursoId, alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno nao esta matriculado neste curso"));

        matricula.aplicarNota(dto.getNota());
        matriculaRepository.save(matricula);
        atualizarNivelContaAluno(matricula.getAluno());

        return toDTO(cursoRepository.findByIdComMatriculas(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + cursoId)));
    }

    @Transactional
    public void deletar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: id=" + id));
        cursoRepository.delete(curso);
    }

    private void atualizarNivelContaAluno(Aluno aluno) {
        int cursosAprovados = matriculaRepository.countByAlunoIdAndSituacao(
                aluno.getId(), SituacaoAluno.APROVADO
        );
        aluno.atualizarNivelConta(cursosAprovados);
        alunoRepository.save(aluno);
    }

    private CursoResponseDTO toDTO(Curso curso) {
        List<CursoResponseDTO.AlunoResumoDTO> alunos = curso.getMatriculas().stream()
                .map(m -> new CursoResponseDTO.AlunoResumoDTO(
                        m.getAluno().getId(),
                        m.getAluno().getNome(),
                        m.getAluno().getRa(),
                        m.getNota(),
                        m.getSituacao() != null ? m.getSituacao().name() : null,
                        m.getAluno().getNivelConta() != null ? m.getAluno().getNivelConta().name() : null
                )).toList();

        Long profId = curso.getProfessor() != null ? curso.getProfessor().getId() : null;
        String profNome = curso.getProfessor() != null ? curso.getProfessor().getNome() : null;

        return new CursoResponseDTO(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                profId,
                profNome,
                alunos
        );
    }
}
