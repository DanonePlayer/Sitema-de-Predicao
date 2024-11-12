package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LogsDto(
    Long id,

    @NotNull(message = "O ID do usuário é obrigatório.")
    Long usuarioId,

    @NotBlank(message = "A ação não pode estar em branco.")
    String acao,

    @NotNull(message = "A data da ação é obrigatória.")
    LocalDateTime dataAcao
) {}
