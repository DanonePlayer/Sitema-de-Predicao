package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public record TesteCoordenadorDto(
    Long id,
    
    @NotNull(message = "O ID do coordenador é obrigatório.")
    Long coordenadorId,
    
    @NotNull(message = "O ID do treinamento é obrigatório.")
    Long treinamentoId,
    
    @NotNull(message = "O campo de desempenho por período não pode ser nulo.")
    @Size(min = 5, max = 100, message = "O desempenho por período deve ter entre 5 e 100 caracteres.")
    String desempenhoDisciplinasPorPeriodo,
    
    @NotNull(message = "O campo de desempenho por turmas não pode ser nulo.")
    @Size(min = 5, max = 100, message = "O desempenho por turmas deve ter entre 5 e 100 caracteres.")
    String desempenhoTurmas,
    
    @Future(message = "A data de previsão deve ser no futuro.")
    Timestamp dataPrevisao
) {}
