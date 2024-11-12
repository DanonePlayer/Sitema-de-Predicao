package br.ufac.edgeneoapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisciplinaDto(
    @NotNull(message = "ID da Disciplina não pode ser nulo")
    Long id,

    @NotNull(message = "O ID do curso é obrigatório.")
    Long cursoId,

    List<Long> alunoIds,

    @NotBlank(message = "O nome da disciplina não pode estar em branco.")
    String disciplinaNome,

    @NotNull(message = "O código da disciplina é obrigatório.")
    @Positive(message = "O código da disciplina deve ser um valor positivo.")
    String disciplinaCodigo,

    @Positive(message = "O período da disciplina deve ser um valor positivo.")
    Integer disciplinaPeriodo,

    @NotNull(message = "A quantidade de créditos da disciplina é obrigatória.")
    @Positive(message = "A quantidade de créditos deve ser um valor positivo.")
    Integer disciplinaCreditos
) {}
