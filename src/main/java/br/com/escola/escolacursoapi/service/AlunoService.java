package br.com.escola.escolacursoapi.service;

import br.com.escola.escolacursoapi.domain.Aluno;
import br.com.escola.escolacursoapi.domain.SituacaoAluno;
import br.com.escola.escolacursoapi.dto.AlunoRequestDTO;
import br.com.escola.escolacursoapi.dto.AlunoResponseDTO;
import br.com.escola.escolacursoapi.repository.AlunoRepository;
import br.com.escola.escolacursoapi.repository.MatriculaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;
    private final MatriculaRepository matriculaRepository;
    private final PasswordEncoder passwordEncoder;

    public AlunoService(AlunoRepository repository,
                        MatriculaRepository matriculaRepository,
                        PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.matriculaRepository = matriculaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponseDTO buscarPorId(Long id) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado: id=" + id));
        return toDTO(aluno);
    }

    @Transactional
    public AlunoResponseDTO criar(AlunoRequestDTO dto) {
        String emailNormalizado = dto.getEmail().trim().toLowerCase();

        if (repository.existsByEmailValor(emailNormalizado)) {
            throw new RuntimeException("E-mail ja cadastrado: " + emailNormalizado);
        }
        if (repository.existsByRaValor(dto.getRa().trim())) {
            throw new RuntimeException("RA ja cadastrado: " + dto.getRa());
        }

        Aluno aluno = new Aluno(
                dto.getNome(),
                emailNormalizado,
                dto.getRa(),
                passwordEncoder.encode(dto.getSenha())
        );

        return toDTO(repository.save(aluno));
    }

    @Transactional
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado: id=" + id));

        String emailNormalizado = dto.getEmail().trim().toLowerCase();

        if (!aluno.getEmail().equals(emailNormalizado)
                && repository.existsByEmailValor(emailNormalizado)) {
            throw new RuntimeException("E-mail ja cadastrado: " + emailNormalizado);
        }
        if (!aluno.getRa().equals(dto.getRa().trim())
                && repository.existsByRaValor(dto.getRa().trim())) {
            throw new RuntimeException("RA ja cadastrado: " + dto.getRa());
        }

        aluno.alterarNome(dto.getNome());
        aluno.alterarEmail(emailNormalizado);
        aluno.alterarRa(dto.getRa());
        aluno.alterarSenhaCriptografada(passwordEncoder.encode(dto.getSenha()));

        return toDTO(repository.save(aluno));
    }

    @Transactional
    public void deletar(Long id) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado: id=" + id));
        repository.delete(aluno);
    }

    private AlunoResponseDTO toDTO(Aluno aluno) {
        int cursosAprovados = matriculaRepository.countByAlunoIdAndSituacao(
                aluno.getId(), SituacaoAluno.APROVADO
        );

        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getRa(),
                aluno.getNivelConta(),
                cursosAprovados
        );
    }
}
