package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.NotBlank;


public record DadosBrutosDto(
    Long id,

    @NotBlank(message = "O código do curso não pode ser vazio.")
    String codCurso,

    @NotBlank(message = "O nome do curso não pode ser vazio.")
    String nomeCurso,

    @NotBlank(message = "O ano de inicio da pesquisa do curso não pode ser vazio.")
    Integer anoInicioPesquisa,

    @NotBlank(message = "O status do curso não pode ser vazio.")
    String status,

    String localArquivo

) {}
