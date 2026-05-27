package br.com.escola.escolacursoapi.service;

import br.com.escola.escolacursoapi.domain.Professor;
import br.com.escola.escolacursoapi.dto.ProfessorRequestDTO;
import br.com.escola.escolacursoapi.dto.ProfessorResponseDTO;
import br.com.escola.escolacursoapi.repository.ProfessorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(ProfessorRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO buscarPorId(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor nao encontrado: id=" + id));
        return toDTO(professor);
    }

    @Transactional
    public ProfessorResponseDTO criar(ProfessorRequestDTO dto) {
        String emailNormalizado = dto.getEmail().trim().toLowerCase();

        if (repository.existsByEmailValor(emailNormalizado)) {
            throw new RuntimeException("E-mail ja cadastrado: " + emailNormalizado);
        }

        Professor professor = new Professor(
                dto.getNome(),
                emailNormalizado,
                passwordEncoder.encode(dto.getSenha())
        );

        return toDTO(repository.save(professor));
    }

    @Transactional
    public ProfessorResponseDTO atualizar(Long id, ProfessorRequestDTO dto) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor nao encontrado: id=" + id));

        String emailNormalizado = dto.getEmail().trim().toLowerCase();
        if (!professor.getEmail().equals(emailNormalizado)
                && repository.existsByEmailValor(emailNormalizado)) {
            throw new RuntimeException("E-mail ja cadastrado: " + emailNormalizado);
        }

        professor.alterarNome(dto.getNome());
        professor.alterarEmail(emailNormalizado);
        professor.alterarSenhaCriptografada(passwordEncoder.encode(dto.getSenha()));

        return toDTO(repository.save(professor));
    }

    @Transactional
    public void deletar(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor nao encontrado: id=" + id));
        repository.delete(professor);
    }

    private ProfessorResponseDTO toDTO(Professor professor) {
        String nomeCurso = professor.getCurso() != null
                ? professor.getCurso().getTitulo()
                : null;
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                nomeCurso
        );
    }
}
