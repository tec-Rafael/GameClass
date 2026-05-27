package br.com.escola.escolacursoapi.controller;

import br.com.escola.escolacursoapi.dto.AplicarNotaRequestDTO;
import br.com.escola.escolacursoapi.dto.CursoRequestDTO;
import br.com.escola.escolacursoapi.dto.CursoResponseDTO;
import br.com.escola.escolacursoapi.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Cursos")
public class CursoRestController {

    private final CursoService service;

    public CursoRestController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os cursos")
    public List<CursoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID, incluindo alunos matriculados e notas por curso")
    public CursoResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar curso")
    public CursoResponseDTO criar(@Valid @RequestBody CursoRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso")
    public CursoResponseDTO atualizar(@PathVariable Long id,
                                      @Valid @RequestBody CursoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @PostMapping("/{cursoId}/alunos/{alunoId}")
    @Operation(summary = "Matricular aluno no curso")
    public CursoResponseDTO adicionarAluno(@PathVariable Long cursoId,
                                           @PathVariable Long alunoId) {
        return service.adicionarAluno(cursoId, alunoId);
    }

    @PatchMapping("/{cursoId}/alunos/{alunoId}/nota")
    @Operation(summary = "Professor aplica nota para um aluno em um curso especifico")
    public CursoResponseDTO aplicarNota(@PathVariable Long cursoId,
                                        @PathVariable Long alunoId,
                                        @Valid @RequestBody AplicarNotaRequestDTO dto) {
        return service.aplicarNota(cursoId, alunoId, dto);
    }

    @DeleteMapping("/{cursoId}/alunos/{alunoId}")
    @Operation(summary = "Desmatricular aluno do curso")
    public CursoResponseDTO removerAluno(@PathVariable Long cursoId,
                                         @PathVariable Long alunoId) {
        return service.removerAluno(cursoId, alunoId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar curso")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
