package withPatterns.strategy.impl;

import withPatterns.strategy.ValidationStrategy;

public class EmailValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(String email) {
        // Expressão regular simples para validar o formato do e-mail
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
