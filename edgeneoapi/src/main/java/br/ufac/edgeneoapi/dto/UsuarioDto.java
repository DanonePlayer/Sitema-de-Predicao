package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Date;

import br.ufac.edgeneoapi.enums.EPerfilUsuario;

public record UsuarioDto(
    Long id,

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    String nome,

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula.")
    @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos uma letra minúscula.")
    @Pattern(regexp = ".*[0-9].*", message = "A senha deve conter pelo menos um número.")
    @Pattern(regexp = ".*[!@#\\$%\\^&\\*].*", message = "A senha deve conter pelo menos um caractere especial.")
    String senha,

    EPerfilUsuario tipo,

    Date dataCadastro,

    String status,

    Timestamp ultimoLogin,

    Boolean cadastroPessoalCompleto, 
    Boolean cadastroHistoricoCompleto,
    Boolean portariaAprovada,
    
    CoordenadorDto coordenador,

    Boolean emailConfirmado,
    String confirmacaoEmailToken,
    Timestamp confirmacaoEmailExpiracao,
    Boolean cadastroComGoogle
) {}
