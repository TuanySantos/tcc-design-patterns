package withPatterns.strategy.impl;

import withPatterns.strategy.ValidationStrategy;

public class CpfValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(String cpf) {
        // Lógica simples de validação do CPF (apenas exemplo)
        return cpf != null && cpf.length() == 11 && cpf.matches("\\d+");
    }
}
