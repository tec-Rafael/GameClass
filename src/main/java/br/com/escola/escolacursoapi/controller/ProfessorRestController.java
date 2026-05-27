package br.com.escola.escolacursoapi.controller;

import br.com.escola.escolacursoapi.dto.ProfessorRequestDTO;
import br.com.escola.escolacursoapi.dto.ProfessorResponseDTO;
import br.com.escola.escolacursoapi.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/professores")
@Tag(name = "Professores")
public class ProfessorRestController {

    private final ProfessorService service;

    public ProfessorRestController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os professores")
    public List<ProfessorResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID")
    public ProfessorResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar professor")
    public ProfessorResponseDTO criar(@Valid @RequestBody ProfessorRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do professor")
    public ProfessorResponseDTO atualizar(@PathVariable Long id,
                                          @Valid @RequestBody ProfessorRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar professor")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
