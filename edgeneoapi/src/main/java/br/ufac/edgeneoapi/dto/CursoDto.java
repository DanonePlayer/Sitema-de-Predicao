package br.ufac.edgeneoapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CursoDto(
    Long id,

    @NotBlank(message = "O nome do curso não pode ser vazio.")
    String cursoNome,

    @NotBlank(message = "A modalidade do curso não pode ser vazia.")
    String cursoModalidade,

    @NotBlank(message = "O turno do curso não pode ser vazio.")
    String cursoTurno,

    @NotBlank(message = "O código do curso não pode ser vazio.")
    String codCurso,

    List<AlunoDto> alunos,
    List<DisciplinaDto> disciplinas
) {}
