package br.ufac.edgeneoapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaForteValidator implements ConstraintValidator<SenhaForte, String> {

    @Override
    public void initialize(SenhaForte constraintAnnotation) {
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        return senha != null && senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
    }
}