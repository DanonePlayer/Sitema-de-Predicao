package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotNull;

public record AdministradorDto(

    @NotNull(message = "ID do Administrador não pode ser nulo")
    Long id,
    @NotNull(message = "Nome não pode ser nulo")
    String nome,
    @NotNull(message = "Centro não pode ser nulo")
    String centro,
    @NotNull(message = "ID do Usuário não pode ser nulo")
    Long usuarioId
) {}
