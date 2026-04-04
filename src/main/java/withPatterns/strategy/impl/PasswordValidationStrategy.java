package withPatterns.strategy.impl;

import withPatterns.strategy.ValidationStrategy;

public class PasswordValidationStrategy implements ValidationStrategy {

    // Definindo uma regra básica para a senha:
    // A senha precisa ter pelo menos 8 caracteres, uma letra maiúscula, uma minúscula e um número.
    @Override
    public boolean validate(String password) {
        if (password == null) {
            return false;
        }

        // Regras para a senha
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean isLengthValid = password.length() >= 8;

        // Verifica cada caractere da senha
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        // A senha será válida se tiver pelo menos uma letra maiúscula,
        // uma letra minúscula, um número e tiver pelo menos 8 caracteres
        return isLengthValid && hasUpperCase && hasLowerCase && hasDigit;
    }
}
