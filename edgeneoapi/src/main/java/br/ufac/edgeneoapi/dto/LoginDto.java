package br.ufac.edgeneoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDto(
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula.")
    @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos uma letra minúscula.")
    @Pattern(regexp = ".*[0-9].*", message = "A senha deve conter pelo menos um número.")
    @Pattern(regexp = ".*[!@#\\$%\\^&\\*].*", message = "A senha deve conter pelo menos um caractere especial.")
    String senha
) {}
