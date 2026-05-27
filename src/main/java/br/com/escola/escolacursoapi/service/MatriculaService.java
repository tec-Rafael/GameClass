package br.com.escola.escolacursoapi.service;

import br.com.escola.escolacursoapi.domain.Matricula;
import br.com.escola.escolacursoapi.dto.MatriculaResponseDTO;
import br.com.escola.escolacursoapi.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarTodas() {
        return repository.findAllComDados().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorCurso(Long cursoId) {
        return repository.findByCursoIdComDados(cursoId).stream().map(this::toDTO).toList();
    }

    private MatriculaResponseDTO toDTO(Matricula matricula) {
        return new MatriculaResponseDTO(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome(),
                matricula.getAluno().getRa(),
                matricula.getCurso().getId(),
                matricula.getCurso().getTitulo(),
                matricula.getNota(),
                matricula.getSituacao() != null ? matricula.getSituacao().name() : null,
                matricula.getAluno().getNivelConta() != null ? matricula.getAluno().getNivelConta().name() : null
        );
    }
}
