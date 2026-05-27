package br.com.escola.escolacursoapi.controller;

import br.com.escola.escolacursoapi.dto.AlunoRequestDTO;
import br.com.escola.escolacursoapi.dto.AlunoResponseDTO;
import br.com.escola.escolacursoapi.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos")
public class AlunoRestController {

    private final AlunoService service;

    public AlunoRestController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos")
    public List<AlunoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    public AlunoResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar aluno com conta BASICO")
    public AlunoResponseDTO criar(@Valid @RequestBody AlunoRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados cadastrais do aluno")
    public AlunoResponseDTO atualizar(@PathVariable Long id,
                                      @Valid @RequestBody AlunoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar aluno")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
