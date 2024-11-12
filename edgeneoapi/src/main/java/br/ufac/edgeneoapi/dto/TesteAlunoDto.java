package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TesteAlunoDto(
    Long id,

    @NotNull(message = "O ID do aluno é obrigatório.")
    Long alunoId,

    @NotNull(message = "O ID do treinamento é obrigatório.")
    Long treinamentoId,

    @NotBlank(message = "O campo de desempenho das disciplinas não pode estar vazio.")
    String desempenhoDisciplinas,

    @NotBlank(message = "O campo de probabilidade de aprovação das disciplinas não pode estar vazio.")
    String probabilidadeAprovacaoDisciplinas,

    @NotBlank(message = "O campo de probabilidade de reprovação das disciplinas não pode estar vazio.")
    String probabilidadeReprovacaoDisciplinas,

    @NotNull(message = "A data de previsão do teste é obrigatória.")
    LocalDateTime testeDataPrevisao
) {}
