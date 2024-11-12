package br.ufac.edgeneoapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Past;

public record AlunoDisciplinasDto(

    @NotNull(message = "ID do Aluno não pode ser nulo")
    Long id,
    @NotNull(message = "ID do Aluno não pode ser nulo")
    Long alunoId,
    @NotNull(message = "ID da Disciplina não pode ser nulo")
    List<DisciplinaDto> disciplinas,
    // @Past(message = "A data de Ingresso deve estar no passado")
    @NotNull(message = "Ano de ingresso não pode ser nulo")
    String anoIngresso,
    String formaIngresso,
    String periodoAtual,
    List<Integer> totalFaltas,
    List<String> situacao,
    List<Double> notas

) {}