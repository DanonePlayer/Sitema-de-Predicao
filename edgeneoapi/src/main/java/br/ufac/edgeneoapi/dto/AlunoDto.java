package br.ufac.edgeneoapi.dto;

import java.util.Date;
import java.util.List;

import br.ufac.edgeneoapi.model.TesteAluno;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record AlunoDto(
    
    @NotNull(message = "ID do Aluno não pode ser nulo")
    Long id,
    @NotNull(message = "ID do Usuário não pode ser nulo")
    Long usuarioId,
    @NotNull(message = "ID do Curso não pode ser nulo")
    Long cursoId,
    List<TesteAluno> testeAlunos,
    List<AlunoDisciplinasDto> alunoDisciplinas, 
    // @NotNull(message = "Sobrenome não pode ser nulo")
    String sobrenome,
    // @NotNull(message = "Possui Deficiência não pode ser nulo")
    Boolean possuiDeficiencia,
    // @NotNull(message = "Tipo de Deficiência não pode ser nulo")
    String deficiencias,
    // @NotNull(message = "CEP não pode ser nulo")
    String cep,
    // @NotNull(message = "Rua não pode ser nulo")
    String rua,
    // @NotNull(message = "Bairro não pode ser nulo")
    String bairro,
    // @NotNull(message = "Cidade não pode ser nulo")
    String cidade,
    // @NotNull(message = "Estado não pode ser nulo")
    String estado,
    // @NotNull(message = "Estado Civil não pode ser nulo")
    String estadoCivil,
    @NotNull(message = "Gênero não pode ser nulo")
    String genero,
    @Past(message = "A data de nascimento deve estar no passado")
    @NotNull(message = "Data de nascimento não pode ser nulo")
    Date dtNascimento,
    @NotNull(message = "Naturalidade não pode ser nulo")
    String naturalidade,
    String etnia,
    String bolsas,
    Date peridoBolsainicio,
    Date peridoBolsafim
) {}
