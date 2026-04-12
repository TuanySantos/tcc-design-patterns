package withpatterns.strategy.impl;

import withpatterns.strategy.ValidationStrategy;

public class EmailValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}