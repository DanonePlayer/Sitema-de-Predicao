package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosProcessadosDto(
    Long id,

    @NotNull(message = "O ID do curso é obrigatório.")
    Long cursoId,

    @NotNull(message = "O ID dos dados brutos é obrigatório.")
    Long dadosBrutosId,

    @NotNull(message = "A idade de início do curso é obrigatória.")
    @Positive(message = "A idade de início do curso deve ser um valor positivo.")
    Integer idadeInicioCurso,

    @NotNull(message = "A duração do curso é obrigatória.")
    @Positive(message = "A duração do curso deve ser um valor positivo.")
    Integer duracaoCurso,

    @NotNull(message = "O percentual do período é obrigatório.")
    @Positive(message = "O percentual do período deve ser um valor positivo.")
    Float periodoPorcentagem,

    @NotNull(message = "O percentual de frequência no período é obrigatório.")
    @Positive(message = "O percentual de frequência no período deve ser um valor positivo.")
    Float percentualFrequenciaPeriodo,

    @NotNull(message = "O CR do histórico recente é obrigatório.")
    @Positive(message = "O CR do histórico recente deve ser um valor positivo.")
    Float historicoRecenteCr,

    @NotNull(message = "O percentual de frequência no histórico recente é obrigatório.")
    @Positive(message = "O percentual de frequência no histórico recente deve ser um valor positivo.")
    Float historicoRecentePercentualFrequencia,

    @NotNull(message = "A média do histórico recente é obrigatória.")
    @Positive(message = "A média do histórico recente deve ser um valor positivo.")
    Float historicoRecenteMedia,

    @NotNull(message = "A quantidade de disciplinas aprovadas no histórico recente é obrigatória.")
    @Positive(message = "A quantidade de disciplinas aprovadas deve ser um valor positivo.")
    Integer historicoRecentesAprovadasQuantidade,

    @NotNull(message = "A média das disciplinas aprovadas no histórico recente é obrigatória.")
    @Positive(message = "A média das disciplinas aprovadas deve ser um valor positivo.")
    Float historicoRecenteAprovadasMedia,

    @NotNull(message = "A quantidade de disciplinas reprovadas no histórico recente é obrigatória.")
    @Positive(message = "A quantidade de disciplinas reprovadas deve ser um valor positivo.")
    Integer historicoRecenteReprovadasQuantidade,

    @NotNull(message = "A média das disciplinas reprovadas no histórico recente é obrigatória.")
    @Positive(message = "A média das disciplinas reprovadas deve ser um valor positivo.")
    Float historicoRecenteReprovadasMedia,

    @NotNull(message = "A quantidade de disciplinas cursadas no histórico recente é obrigatória.")
    @Positive(message = "A quantidade de disciplinas cursadas deve ser um valor positivo.")
    Integer historicoRecenteCursadas,

    @NotBlank(message = "A classe de rendimento no histórico recente não pode ser vazia.")
    String historicoRecenteClasseRendimento,

    @NotBlank(message = "A classe de desempenho no histórico recente não pode ser vazia.")
    String historicoRecenteClasseDesempenho,

    @NotBlank(message = "A classe de disciplinas cursadas no histórico recente não pode ser vazia.")
    String historicoRecenteClasseDisciplinasCursadas,

    @NotNull(message = "O CR do histórico geral é obrigatório.")
    @Positive(message = "O CR do histórico geral deve ser um valor positivo.")
    Float historicoGeralCr,

    @NotNull(message = "O percentual de frequência no histórico geral é obrigatório.")
    @Positive(message = "O percentual de frequência no histórico geral deve ser um valor positivo.")
    Float historicoGeralPercentualFrequencia,

    @NotNull(message = "A média do histórico geral é obrigatória.")
    @Positive(message = "A média do histórico geral deve ser um valor positivo.")
    Float historicoGeralMedia,

    @NotNull(message = "A quantidade de disciplinas aprovadas no histórico geral é obrigatória.")
    @Positive(message = "A quantidade de disciplinas aprovadas deve ser um valor positivo.")
    Integer historicoGeralAprovadasQuantidade,

    @NotNull(message = "A média das disciplinas aprovadas no histórico geral é obrigatória.")
    @Positive(message = "A média das disciplinas aprovadas deve ser um valor positivo.")
    Float historicoGeralAprovadasMedia,

    @NotNull(message = "A quantidade de disciplinas reprovadas no histórico geral é obrigatória.")
    @Positive(message = "A quantidade de disciplinas reprovadas deve ser um valor positivo.")
    Integer historicoGeralReprovadasQuantidade,

    @NotNull(message = "A média das disciplinas reprovadas no histórico geral é obrigatória.")
    @Positive(message = "A média das disciplinas reprovadas deve ser um valor positivo.")
    Float historicoGeralReprovadasMedia,

    @NotNull(message = "A quantidade de disciplinas cursadas no histórico geral é obrigatória.")
    @Positive(message = "A quantidade de disciplinas cursadas deve ser um valor positivo.")
    Integer historicoGeralCursadas
) {}
