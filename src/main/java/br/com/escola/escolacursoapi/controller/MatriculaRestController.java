package br.com.escola.escolacursoapi.controller;

import br.com.escola.escolacursoapi.dto.MatriculaResponseDTO;
import br.com.escola.escolacursoapi.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@Tag(name = "Matriculas")
public class MatriculaRestController {

    private final MatriculaService service;

    public MatriculaRestController(MatriculaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as matriculas")
    public List<MatriculaResponseDTO> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Listar matriculas de um curso")
    public List<MatriculaResponseDTO> listarPorCurso(@PathVariable Long cursoId) {
        return service.listarPorCurso(cursoId);
    }
}
