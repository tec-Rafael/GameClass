package br.com.escola.escolacursoapi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class AplicarNotaRequestDTO {

    @NotNull(message = "ID do professor e obrigatorio")
    private Long professorId;

    @NotNull(message = "Nota e obrigatoria")
    @DecimalMin(value = "0.0", message = "Nota minima e 0.0")
    @DecimalMax(value = "10.0", message = "Nota maxima e 10.0")
    private Double nota;

    public AplicarNotaRequestDTO() {}

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }
}
