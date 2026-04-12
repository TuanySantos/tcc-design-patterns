package withpatterns.strategy.impl;

import withpatterns.strategy.ValidationStrategy;

public class CpfValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}