package br.ufac.edgeneoapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufac.edgeneoapi.enums.EStatus;
// import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoordenadorDto(
    Long id,

    @NotNull(message = "O ID do usuário não pode ser nulo")
    @JsonProperty("usuario_id")
    Long usuario_id,

    @JsonProperty("curso_id")
    // @NotNull(message = "O ID do curso não pode ser nulo")
    Long curso_id,

    // @NotBlank(message = "O número da portaria não pode ser vazio")
    String portaria,

    // @NotNull(message = "O status do coordenador não pode ser nulo")
    EStatus status
) {}
