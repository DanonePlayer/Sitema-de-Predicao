package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

public record TreinamentoDto(
    Long id,

    @NotNull(message = "O ID do curso é obrigatório.")
    Long cursoId,

    @NotNull(message = "O ID do administrador é obrigatório.")
    Long administradorId,

    @NotNull(message = "O ID dos dados brutos é obrigatório.")
    Long dadosBrutosId,

    @NotNull(message = "O ID dos dados processados é obrigatório.")
    Long dadosProcessadosId,

    @NotNull(message = "O algoritmo utilizado é obrigatório.")
    String algoritmoUtilizado,

    @NotNull(message = "A data e hora são obrigatórias.")
    Timestamp dataHora,

    @NotNull(message = "A acurácia é obrigatória.")
    Float acuracia,

    @NotNull(message = "A precisão é obrigatória.")
    Float precisao,

    @NotNull(message = "O tempo de execução é obrigatório.")
    Float tempoExecucao
) {}
